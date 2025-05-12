#include <string>

#include "llvm/IR/IRBuilder.h"
#include "llvm/IR/InstrTypes.h"
#include "llvm/IR/Instruction.h"
#include "llvm/IR/Instructions.h"
#include "llvm/IR/Type.h"
#include "llvm/IR/Module.h"
#include "llvm/Passes/PassBuilder.h"
#include "llvm/Passes/PassPlugin.h"

using namespace llvm;

template <typename T>
std::string getAsString(T *type) {
    std::string typeStr;
    llvm::raw_string_ostream rso(typeStr);
    type->print(rso);
    return rso.str();
}

struct TracerPass : public PassInfoMixin<TracerPass> {
    PreservedAnalyses run(Module &M, ModuleAnalysisManager &AM) {
        LLVMContext &Ctx = M.getContext();
        IRBuilder<> builder(Ctx);
        FunctionCallee printFunc = M.getOrInsertFunction("puts", Type::getInt32Ty(Ctx), Type::getInt8Ty(Ctx)->getPointerTo());
        for (auto &func : M) if (!func.isDeclaration()) {
            for (auto &block : func) {
                for (auto &instruction : block) if (!isa<PHINode>(&instruction)) {
                    builder.SetInsertPoint(&instruction);
                    //   std::string out;
                    //   out += instruction.getOpcodeName();
                    //   for (auto &operand : instruction.operands()) {
                    //     out += " ";
                    //     out += getAsString(operand.get()/*->getType()*/);
                    //   }
                    //   Value *iPtr = builder.CreateGlobalStringPtr(out);
                    //   builder.CreateCall(printFunc,{iPtr});
                    for (auto &operand : instruction.operands()) if (auto *instr_operand = dyn_cast<Instruction>(operand)) {
                        Value *iPtr = builder.CreateGlobalStringPtr(std::string() + instruction.getOpcodeName() + " <- " + instr_operand->getOpcodeName());
                        builder.CreateCall(printFunc, {iPtr});
                    }
                }
            }
        }
        return PreservedAnalyses::none();
    };

    static bool isRequired() { return true; }
};

PassPluginLibraryInfo getPassPluginInfo() {
    const auto callback = [](PassBuilder &PB) {
        PB.registerOptimizerLastEPCallback([&](ModulePassManager &MPM, auto) {
            MPM.addPass(TracerPass{});
            return true;
        });
    };

    return {LLVM_PLUGIN_API_VERSION, "Tracer", "0.0.1", callback};
};

extern "C" LLVM_ATTRIBUTE_WEAK PassPluginLibraryInfo llvmGetPassPluginInfo() {
    return getPassPluginInfo();
}