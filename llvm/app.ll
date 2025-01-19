; ModuleID = '../app/app.c'
source_filename = "../app/app.c"
target datalayout = "e-m:e-p270:32:32-p271:32:32-p272:64:64-i64:64-i128:128-f80:128-n8:16:32:64-S128"
target triple = "x86_64-pc-linux-gnu"

@coeff = dso_local constant i32 2, align 4

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define dso_local i32 @color(i32 noundef %0) #0 {
  %2 = alloca i32, align 4
  %3 = alloca i32, align 4
  store i32 %0, ptr %3, align 4
  %4 = load i32, ptr %3, align 4
  %5 = icmp eq i32 %4, 0
  br i1 %5, label %6, label %7

6:                                                ; preds = %1
  store i32 -8947849, ptr %2, align 4
  br label %20

7:                                                ; preds = %1
  %8 = load i32, ptr %3, align 4
  %9 = icmp eq i32 %8, 1
  br i1 %9, label %10, label %11

10:                                               ; preds = %7
  store i32 -65536, ptr %2, align 4
  br label %20

11:                                               ; preds = %7
  %12 = load i32, ptr %3, align 4
  %13 = icmp eq i32 %12, 2
  br i1 %13, label %14, label %15

14:                                               ; preds = %11
  store i32 -35072, ptr %2, align 4
  br label %20

15:                                               ; preds = %11
  %16 = load i32, ptr %3, align 4
  %17 = icmp eq i32 %16, 3
  br i1 %17, label %18, label %19

18:                                               ; preds = %15
  store i32 -256, ptr %2, align 4
  br label %20

19:                                               ; preds = %15
  store i32 -16776961, ptr %2, align 4
  br label %20

20:                                               ; preds = %19, %18, %14, %10, %6
  %21 = load i32, ptr %2, align 4
  ret i32 %21
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define dso_local void @app() #0 {
  %1 = alloca ptr, align 8
  %2 = alloca i32, align 4
  %3 = alloca i32, align 4
  %4 = alloca i32, align 4
  %5 = alloca i32, align 4
  %6 = alloca i32, align 4
  %7 = alloca i32, align 4
  %8 = alloca i32, align 4
  %9 = alloca i32, align 4
  %10 = alloca i32, align 4
  %11 = alloca i32, align 4
  %12 = alloca i32, align 4
  %13 = alloca i32, align 4
  %14 = alloca i32, align 4
  %15 = alloca i32, align 4
  %16 = alloca i32, align 4
  %17 = alloca i32, align 4
  %18 = alloca i32, align 4
  %19 = call ptr @llvm.stacksave.p0()
  store ptr %19, ptr %1, align 8
  %20 = alloca i8, i64 32768, align 16
  store i32 0, ptr %2, align 4
  br label %21

21:                                               ; preds = %47, %0
  %22 = load i32, ptr %2, align 4
  %23 = icmp slt i32 %22, 128
  br i1 %23, label %24, label %50

24:                                               ; preds = %21
  store i32 0, ptr %3, align 4
  br label %25

25:                                               ; preds = %43, %24
  %26 = load i32, ptr %3, align 4
  %27 = icmp slt i32 %26, 256
  br i1 %27, label %28, label %46

28:                                               ; preds = %25
  %29 = call i32 (...) @simRand()
  %30 = srem i32 %29, 1024
  %31 = add nsw i32 %30, 1024
  %32 = srem i32 %31, 1024
  store i32 %32, ptr %4, align 4
  %33 = load i32, ptr %4, align 4
  %34 = sdiv i32 %33, 256
  %35 = trunc i32 %34 to i8
  %36 = load i32, ptr %3, align 4
  %37 = sext i32 %36 to i64
  %38 = mul nsw i64 %37, 128
  %39 = getelementptr inbounds i8, ptr %20, i64 %38
  %40 = load i32, ptr %2, align 4
  %41 = sext i32 %40 to i64
  %42 = getelementptr inbounds i8, ptr %39, i64 %41
  store i8 %35, ptr %42, align 1
  br label %43

43:                                               ; preds = %28
  %44 = load i32, ptr %3, align 4
  %45 = add nsw i32 %44, 1
  store i32 %45, ptr %3, align 4
  br label %25, !llvm.loop !6

46:                                               ; preds = %25
  br label %47

47:                                               ; preds = %46
  %48 = load i32, ptr %2, align 4
  %49 = add nsw i32 %48, 1
  store i32 %49, ptr %2, align 4
  br label %21, !llvm.loop !8

50:                                               ; preds = %21
  store i32 0, ptr %5, align 4
  br label %51

51:                                               ; preds = %221, %50
  %52 = load i32, ptr %5, align 4
  %53 = icmp slt i32 %52, 1000000000
  br i1 %53, label %54, label %224

54:                                               ; preds = %51
  store i32 0, ptr %6, align 4
  br label %55

55:                                               ; preds = %190, %54
  %56 = load i32, ptr %6, align 4
  %57 = icmp slt i32 %56, 128
  br i1 %57, label %58, label %193

58:                                               ; preds = %55
  store i32 0, ptr %7, align 4
  br label %59

59:                                               ; preds = %186, %58
  %60 = load i32, ptr %7, align 4
  %61 = icmp slt i32 %60, 256
  br i1 %61, label %62, label %189

62:                                               ; preds = %59
  %63 = load i32, ptr %7, align 4
  %64 = sext i32 %63 to i64
  %65 = mul nsw i64 %64, 128
  %66 = getelementptr inbounds i8, ptr %20, i64 %65
  %67 = load i32, ptr %6, align 4
  %68 = sext i32 %67 to i64
  %69 = getelementptr inbounds i8, ptr %66, i64 %68
  %70 = load i8, ptr %69, align 1
  %71 = sext i8 %70 to i32
  %72 = and i32 %71, 3
  store i32 %72, ptr %8, align 4
  %73 = load i32, ptr %8, align 4
  %74 = icmp eq i32 %73, 2
  br i1 %74, label %75, label %76

75:                                               ; preds = %62
  store i32 3, ptr %9, align 4
  br label %146

76:                                               ; preds = %62
  %77 = load i32, ptr %8, align 4
  %78 = icmp eq i32 %77, 3
  br i1 %78, label %79, label %80

79:                                               ; preds = %76
  store i32 0, ptr %9, align 4
  br label %145

80:                                               ; preds = %76
  store i32 0, ptr %10, align 4
  store i32 -2, ptr %11, align 4
  br label %81

81:                                               ; preds = %118, %80
  %82 = load i32, ptr %11, align 4
  %83 = icmp sle i32 %82, 2
  br i1 %83, label %84, label %121

84:                                               ; preds = %81
  store i32 -2, ptr %12, align 4
  br label %85

85:                                               ; preds = %114, %84
  %86 = load i32, ptr %12, align 4
  %87 = icmp sle i32 %86, 2
  br i1 %87, label %88, label %117

88:                                               ; preds = %85
  %89 = load i32, ptr %7, align 4
  %90 = load i32, ptr %12, align 4
  %91 = add nsw i32 %89, %90
  %92 = add nsw i32 %91, 512
  %93 = srem i32 %92, 256
  store i32 %93, ptr %13, align 4
  %94 = load i32, ptr %6, align 4
  %95 = load i32, ptr %11, align 4
  %96 = add nsw i32 %94, %95
  %97 = add nsw i32 %96, 256
  %98 = srem i32 %97, 256
  store i32 %98, ptr %14, align 4
  %99 = load i32, ptr %13, align 4
  %100 = sext i32 %99 to i64
  %101 = mul nsw i64 %100, 128
  %102 = getelementptr inbounds i8, ptr %20, i64 %101
  %103 = load i32, ptr %14, align 4
  %104 = sext i32 %103 to i64
  %105 = getelementptr inbounds i8, ptr %102, i64 %104
  %106 = load i8, ptr %105, align 1
  %107 = sext i8 %106 to i32
  %108 = and i32 %107, 3
  %109 = icmp eq i32 %108, 1
  br i1 %109, label %110, label %113

110:                                              ; preds = %88
  %111 = load i32, ptr %10, align 4
  %112 = add nsw i32 %111, 1
  store i32 %112, ptr %10, align 4
  br label %113

113:                                              ; preds = %110, %88
  br label %114

114:                                              ; preds = %113
  %115 = load i32, ptr %12, align 4
  %116 = add nsw i32 %115, 1
  store i32 %116, ptr %12, align 4
  br label %85, !llvm.loop !9

117:                                              ; preds = %85
  br label %118

118:                                              ; preds = %117
  %119 = load i32, ptr %11, align 4
  %120 = add nsw i32 %119, 1
  store i32 %120, ptr %11, align 4
  br label %81, !llvm.loop !10

121:                                              ; preds = %81
  %122 = load i32, ptr %8, align 4
  %123 = icmp eq i32 %122, 0
  br i1 %123, label %124, label %134

124:                                              ; preds = %121
  %125 = load i32, ptr %10, align 4
  %126 = icmp sle i32 6, %125
  br i1 %126, label %127, label %130

127:                                              ; preds = %124
  %128 = load i32, ptr %10, align 4
  %129 = icmp sle i32 %128, 7
  br label %130

130:                                              ; preds = %127, %124
  %131 = phi i1 [ false, %124 ], [ %129, %127 ]
  %132 = zext i1 %131 to i64
  %133 = select i1 %131, i32 1, i32 0
  store i32 %133, ptr %9, align 4
  br label %144

134:                                              ; preds = %121
  %135 = load i32, ptr %10, align 4
  %136 = icmp sle i32 8, %135
  br i1 %136, label %137, label %140

137:                                              ; preds = %134
  %138 = load i32, ptr %10, align 4
  %139 = icmp sle i32 %138, 12
  br label %140

140:                                              ; preds = %137, %134
  %141 = phi i1 [ false, %134 ], [ %139, %137 ]
  %142 = zext i1 %141 to i64
  %143 = select i1 %141, i32 1, i32 2
  store i32 %143, ptr %9, align 4
  br label %144

144:                                              ; preds = %140, %130
  br label %145

145:                                              ; preds = %144, %79
  br label %146

146:                                              ; preds = %145, %75
  %147 = load i32, ptr %9, align 4
  %148 = shl i32 %147, 2
  %149 = load i32, ptr %7, align 4
  %150 = sext i32 %149 to i64
  %151 = mul nsw i64 %150, 128
  %152 = getelementptr inbounds i8, ptr %20, i64 %151
  %153 = load i32, ptr %6, align 4
  %154 = sext i32 %153 to i64
  %155 = getelementptr inbounds i8, ptr %152, i64 %154
  %156 = load i8, ptr %155, align 1
  %157 = sext i8 %156 to i32
  %158 = add nsw i32 %157, %148
  %159 = trunc i32 %158 to i8
  store i8 %159, ptr %155, align 1
  store i32 0, ptr %15, align 4
  br label %160

160:                                              ; preds = %182, %146
  %161 = load i32, ptr %15, align 4
  %162 = icmp slt i32 %161, 2
  br i1 %162, label %163, label %185

163:                                              ; preds = %160
  store i32 0, ptr %16, align 4
  br label %164

164:                                              ; preds = %178, %163
  %165 = load i32, ptr %16, align 4
  %166 = icmp slt i32 %165, 2
  br i1 %166, label %167, label %181

167:                                              ; preds = %164
  %168 = load i32, ptr %7, align 4
  %169 = mul nsw i32 %168, 2
  %170 = load i32, ptr %16, align 4
  %171 = add nsw i32 %169, %170
  %172 = load i32, ptr %6, align 4
  %173 = mul nsw i32 %172, 2
  %174 = load i32, ptr %15, align 4
  %175 = add nsw i32 %173, %174
  %176 = load i32, ptr %9, align 4
  %177 = call i32 @color(i32 noundef %176)
  call void @simPutPixel(i32 noundef %171, i32 noundef %175, i32 noundef %177)
  br label %178

178:                                              ; preds = %167
  %179 = load i32, ptr %16, align 4
  %180 = add nsw i32 %179, 1
  store i32 %180, ptr %16, align 4
  br label %164, !llvm.loop !11

181:                                              ; preds = %164
  br label %182

182:                                              ; preds = %181
  %183 = load i32, ptr %15, align 4
  %184 = add nsw i32 %183, 1
  store i32 %184, ptr %15, align 4
  br label %160, !llvm.loop !12

185:                                              ; preds = %160
  br label %186

186:                                              ; preds = %185
  %187 = load i32, ptr %7, align 4
  %188 = add nsw i32 %187, 1
  store i32 %188, ptr %7, align 4
  br label %59, !llvm.loop !13

189:                                              ; preds = %59
  br label %190

190:                                              ; preds = %189
  %191 = load i32, ptr %6, align 4
  %192 = add nsw i32 %191, 1
  store i32 %192, ptr %6, align 4
  br label %55, !llvm.loop !14

193:                                              ; preds = %55
  call void (...) @simFlush()
  store i32 0, ptr %17, align 4
  br label %194

194:                                              ; preds = %217, %193
  %195 = load i32, ptr %17, align 4
  %196 = icmp slt i32 %195, 128
  br i1 %196, label %197, label %220

197:                                              ; preds = %194
  store i32 0, ptr %18, align 4
  br label %198

198:                                              ; preds = %213, %197
  %199 = load i32, ptr %18, align 4
  %200 = icmp slt i32 %199, 256
  br i1 %200, label %201, label %216

201:                                              ; preds = %198
  %202 = load i32, ptr %18, align 4
  %203 = sext i32 %202 to i64
  %204 = mul nsw i64 %203, 128
  %205 = getelementptr inbounds i8, ptr %20, i64 %204
  %206 = load i32, ptr %17, align 4
  %207 = sext i32 %206 to i64
  %208 = getelementptr inbounds i8, ptr %205, i64 %207
  %209 = load i8, ptr %208, align 1
  %210 = sext i8 %209 to i32
  %211 = ashr i32 %210, 2
  %212 = trunc i32 %211 to i8
  store i8 %212, ptr %208, align 1
  br label %213

213:                                              ; preds = %201
  %214 = load i32, ptr %18, align 4
  %215 = add nsw i32 %214, 1
  store i32 %215, ptr %18, align 4
  br label %198, !llvm.loop !15

216:                                              ; preds = %198
  br label %217

217:                                              ; preds = %216
  %218 = load i32, ptr %17, align 4
  %219 = add nsw i32 %218, 1
  store i32 %219, ptr %17, align 4
  br label %194, !llvm.loop !16

220:                                              ; preds = %194
  br label %221

221:                                              ; preds = %220
  %222 = load i32, ptr %5, align 4
  %223 = add nsw i32 %222, 1
  store i32 %223, ptr %5, align 4
  br label %51, !llvm.loop !17

224:                                              ; preds = %51
  %225 = load ptr, ptr %1, align 8
  call void @llvm.stackrestore.p0(ptr %225)
  ret void
}

; Function Attrs: nocallback nofree nosync nounwind willreturn
declare ptr @llvm.stacksave.p0() #1

declare i32 @simRand(...) #2

declare void @simPutPixel(i32 noundef, i32 noundef, i32 noundef) #2

declare void @simFlush(...) #2

; Function Attrs: nocallback nofree nosync nounwind willreturn
declare void @llvm.stackrestore.p0(ptr) #1

attributes #0 = { noinline nounwind optnone sspstrong uwtable "frame-pointer"="all" "min-legal-vector-width"="0" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cmov,+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #1 = { nocallback nofree nosync nounwind willreturn }
attributes #2 = { "frame-pointer"="all" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cmov,+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }

!llvm.module.flags = !{!0, !1, !2, !3, !4}
!llvm.ident = !{!5}

!0 = !{i32 1, !"wchar_size", i32 4}
!1 = !{i32 8, !"PIC Level", i32 2}
!2 = !{i32 7, !"PIE Level", i32 2}
!3 = !{i32 7, !"uwtable", i32 2}
!4 = !{i32 7, !"frame-pointer", i32 2}
!5 = !{!"clang version 18.1.8"}
!6 = distinct !{!6, !7}
!7 = !{!"llvm.loop.mustprogress"}
!8 = distinct !{!8, !7}
!9 = distinct !{!9, !7}
!10 = distinct !{!10, !7}
!11 = distinct !{!11, !7}
!12 = distinct !{!12, !7}
!13 = distinct !{!13, !7}
!14 = distinct !{!14, !7}
!15 = distinct !{!15, !7}
!16 = distinct !{!16, !7}
!17 = distinct !{!17, !7}
