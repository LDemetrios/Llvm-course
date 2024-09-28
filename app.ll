; ModuleID = 'app.c'
source_filename = "app.c"
target datalayout = "e-m:e-p270:32:32-p271:32:32-p272:64:64-i64:64-i128:128-f80:128-n8:16:32:64-S128"
target triple = "x86_64-pc-linux-gnu"

@switch.table.color = private unnamed_addr constant [4 x i32] [i32 -8947849, i32 -65536, i32 -35072, i32 -256], align 4

; Function Attrs: mustprogress nofree norecurse nosync nounwind sspstrong willreturn memory(none) uwtable
define dso_local noundef i32 @color(i32 noundef %0) local_unnamed_addr #0 {
  %2 = icmp ult i32 %0, 4
  br i1 %2, label %3, label %7

3:                                                ; preds = %1
  %4 = zext nneg i32 %0 to i64
  %5 = getelementptr inbounds [4 x i32], ptr @switch.table.color, i64 0, i64 %4
  %6 = load i32, ptr %5, align 4
  br label %7

7:                                                ; preds = %1, %3
  %8 = phi i32 [ %6, %3 ], [ -16776961, %1 ]
  ret i32 %8
}

; Function Attrs: nounwind sspstrong uwtable
define dso_local void @app() local_unnamed_addr #1 {
  %1 = alloca [256 x [128 x i8]], align 16
  tail call void (...) @simInit() #4
  call void @llvm.lifetime.start.p0(i64 32768, ptr nonnull %1) #4
  br label %2

2:                                                ; preds = %0, %4
  %3 = phi i64 [ 0, %0 ], [ %5, %4 ]
  br label %7

4:                                                ; preds = %7
  %5 = add nuw nsw i64 %3, 1
  %6 = icmp eq i64 %5, 128
  br i1 %6, label %16, label %2, !llvm.loop !5

7:                                                ; preds = %2, %7
  %8 = phi i64 [ 0, %2 ], [ %14, %7 ]
  %9 = tail call i32 (...) @simRand() #4
  %10 = lshr i32 %9, 8
  %11 = trunc i32 %10 to i8
  %12 = and i8 %11, 3
  %13 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %8, i64 %3
  store i8 %12, ptr %13, align 1, !tbaa !7
  %14 = add nuw nsw i64 %8, 1
  %15 = icmp eq i64 %14, 256
  br i1 %15, label %4, label %7, !llvm.loop !10

16:                                               ; preds = %4, %255
  %17 = phi i32 [ %256, %255 ], [ 0, %4 ]
  br label %19

18:                                               ; preds = %255
  tail call void (...) @simExit() #4
  call void @llvm.lifetime.end.p0(i64 32768, ptr nonnull %1) #4
  ret void

19:                                               ; preds = %16, %44
  %20 = phi i64 [ 0, %16 ], [ %45, %44 ]
  %21 = shl nuw nsw i64 %20, 1
  %22 = trunc i64 %21 to i32
  %23 = trunc i64 %21 to i32
  %24 = or disjoint i32 %23, 1
  %25 = add nuw i64 %20, 254
  %26 = and i64 %25, 255
  %27 = add nuw i64 %20, 255
  %28 = and i64 %27, 255
  %29 = and i64 %20, 255
  %30 = add nuw i64 %20, 1
  %31 = and i64 %30, 255
  %32 = add nuw i64 %20, 2
  %33 = and i64 %32, 255
  %34 = trunc i64 %21 to i32
  %35 = trunc i64 %21 to i32
  %36 = or disjoint i32 %35, 1
  %37 = trunc i64 %21 to i32
  %38 = trunc i64 %21 to i32
  %39 = or disjoint i32 %38, 1
  %40 = trunc i64 %21 to i32
  %41 = trunc i64 %21 to i32
  %42 = or disjoint i32 %41, 1
  br label %47

43:                                               ; preds = %44
  tail call void (...) @simFlush() #4
  br label %253

44:                                               ; preds = %247
  %45 = add nuw nsw i64 %20, 1
  %46 = icmp eq i64 %45, 128
  br i1 %46, label %43, label %19, !llvm.loop !11

47:                                               ; preds = %19, %247
  %48 = phi i64 [ 0, %19 ], [ %251, %247 ]
  %49 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %48, i64 %20
  %50 = load i8, ptr %49, align 1, !tbaa !7
  %51 = and i8 %50, 3
  switch i8 %51, label %58 [
    i8 2, label %52
    i8 3, label %218
  ]

52:                                               ; preds = %47
  %53 = add i8 %50, 12
  store i8 %53, ptr %49, align 1, !tbaa !7
  %54 = shl nuw nsw i64 %48, 1
  %55 = trunc i64 %54 to i32
  tail call void @simPutPixel(i32 noundef %55, i32 noundef %22, i32 noundef -256) #4
  %56 = trunc i64 %54 to i32
  %57 = or disjoint i32 %56, 1
  tail call void @simPutPixel(i32 noundef %57, i32 noundef %22, i32 noundef -256) #4
  tail call void @simPutPixel(i32 noundef %55, i32 noundef %24, i32 noundef -256) #4
  br label %247

58:                                               ; preds = %47
  %59 = add nuw i64 %48, 254
  %60 = and i64 %59, 255
  %61 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %60, i64 %26
  %62 = load i8, ptr %61, align 1, !tbaa !7
  %63 = and i8 %62, 3
  %64 = icmp eq i8 %63, 1
  %65 = zext i1 %64 to i32
  %66 = add nuw i64 %48, 255
  %67 = and i64 %66, 255
  %68 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %67, i64 %26
  %69 = load i8, ptr %68, align 1, !tbaa !7
  %70 = and i8 %69, 3
  %71 = icmp eq i8 %70, 1
  %72 = zext i1 %71 to i32
  %73 = add nuw nsw i32 %65, %72
  %74 = and i64 %48, 255
  %75 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %74, i64 %26
  %76 = load i8, ptr %75, align 1, !tbaa !7
  %77 = and i8 %76, 3
  %78 = icmp eq i8 %77, 1
  %79 = zext i1 %78 to i32
  %80 = add nuw nsw i32 %73, %79
  %81 = add nuw i64 %48, 1
  %82 = and i64 %81, 255
  %83 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %82, i64 %26
  %84 = load i8, ptr %83, align 1, !tbaa !7
  %85 = and i8 %84, 3
  %86 = icmp eq i8 %85, 1
  %87 = zext i1 %86 to i32
  %88 = add nuw nsw i32 %80, %87
  %89 = add nuw i64 %48, 2
  %90 = and i64 %89, 255
  %91 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %90, i64 %26
  %92 = load i8, ptr %91, align 1, !tbaa !7
  %93 = and i8 %92, 3
  %94 = icmp eq i8 %93, 1
  %95 = zext i1 %94 to i32
  %96 = add nuw nsw i32 %88, %95
  %97 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %60, i64 %28
  %98 = load i8, ptr %97, align 1, !tbaa !7
  %99 = and i8 %98, 3
  %100 = icmp eq i8 %99, 1
  %101 = zext i1 %100 to i32
  %102 = add nuw nsw i32 %96, %101
  %103 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %67, i64 %28
  %104 = load i8, ptr %103, align 1, !tbaa !7
  %105 = and i8 %104, 3
  %106 = icmp eq i8 %105, 1
  %107 = zext i1 %106 to i32
  %108 = add nuw nsw i32 %102, %107
  %109 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %74, i64 %28
  %110 = load i8, ptr %109, align 1, !tbaa !7
  %111 = and i8 %110, 3
  %112 = icmp eq i8 %111, 1
  %113 = zext i1 %112 to i32
  %114 = add nuw nsw i32 %108, %113
  %115 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %82, i64 %28
  %116 = load i8, ptr %115, align 1, !tbaa !7
  %117 = and i8 %116, 3
  %118 = icmp eq i8 %117, 1
  %119 = zext i1 %118 to i32
  %120 = add nuw nsw i32 %114, %119
  %121 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %90, i64 %28
  %122 = load i8, ptr %121, align 1, !tbaa !7
  %123 = and i8 %122, 3
  %124 = icmp eq i8 %123, 1
  %125 = zext i1 %124 to i32
  %126 = add nuw nsw i32 %120, %125
  %127 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %60, i64 %29
  %128 = load i8, ptr %127, align 1, !tbaa !7
  %129 = and i8 %128, 3
  %130 = icmp eq i8 %129, 1
  %131 = zext i1 %130 to i32
  %132 = add nuw nsw i32 %126, %131
  %133 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %67, i64 %29
  %134 = load i8, ptr %133, align 1, !tbaa !7
  %135 = and i8 %134, 3
  %136 = icmp eq i8 %135, 1
  %137 = zext i1 %136 to i32
  %138 = add nuw nsw i32 %132, %137
  %139 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %74, i64 %29
  %140 = load i8, ptr %139, align 1, !tbaa !7
  %141 = and i8 %140, 3
  %142 = icmp eq i8 %141, 1
  %143 = zext i1 %142 to i32
  %144 = add nuw nsw i32 %138, %143
  %145 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %82, i64 %29
  %146 = load i8, ptr %145, align 1, !tbaa !7
  %147 = and i8 %146, 3
  %148 = icmp eq i8 %147, 1
  %149 = zext i1 %148 to i32
  %150 = add nuw nsw i32 %144, %149
  %151 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %90, i64 %29
  %152 = load i8, ptr %151, align 1, !tbaa !7
  %153 = and i8 %152, 3
  %154 = icmp eq i8 %153, 1
  %155 = zext i1 %154 to i32
  %156 = add nuw nsw i32 %150, %155
  %157 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %60, i64 %31
  %158 = load i8, ptr %157, align 1, !tbaa !7
  %159 = and i8 %158, 3
  %160 = icmp eq i8 %159, 1
  %161 = zext i1 %160 to i32
  %162 = add nuw nsw i32 %156, %161
  %163 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %67, i64 %31
  %164 = load i8, ptr %163, align 1, !tbaa !7
  %165 = and i8 %164, 3
  %166 = icmp eq i8 %165, 1
  %167 = zext i1 %166 to i32
  %168 = add nuw nsw i32 %162, %167
  %169 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %74, i64 %31
  %170 = load i8, ptr %169, align 1, !tbaa !7
  %171 = and i8 %170, 3
  %172 = icmp eq i8 %171, 1
  %173 = zext i1 %172 to i32
  %174 = add nuw nsw i32 %168, %173
  %175 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %82, i64 %31
  %176 = load i8, ptr %175, align 1, !tbaa !7
  %177 = and i8 %176, 3
  %178 = icmp eq i8 %177, 1
  %179 = zext i1 %178 to i32
  %180 = add nuw nsw i32 %174, %179
  %181 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %90, i64 %31
  %182 = load i8, ptr %181, align 1, !tbaa !7
  %183 = and i8 %182, 3
  %184 = icmp eq i8 %183, 1
  %185 = zext i1 %184 to i32
  %186 = add nuw nsw i32 %180, %185
  %187 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %60, i64 %33
  %188 = load i8, ptr %187, align 1, !tbaa !7
  %189 = and i8 %188, 3
  %190 = icmp eq i8 %189, 1
  %191 = zext i1 %190 to i32
  %192 = add nuw nsw i32 %186, %191
  %193 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %67, i64 %33
  %194 = load i8, ptr %193, align 1, !tbaa !7
  %195 = and i8 %194, 3
  %196 = icmp eq i8 %195, 1
  %197 = zext i1 %196 to i32
  %198 = add nuw nsw i32 %192, %197
  %199 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %74, i64 %33
  %200 = load i8, ptr %199, align 1, !tbaa !7
  %201 = and i8 %200, 3
  %202 = icmp eq i8 %201, 1
  %203 = zext i1 %202 to i32
  %204 = add nuw nsw i32 %198, %203
  %205 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %82, i64 %33
  %206 = load i8, ptr %205, align 1, !tbaa !7
  %207 = and i8 %206, 3
  %208 = icmp eq i8 %207, 1
  %209 = zext i1 %208 to i32
  %210 = add nuw nsw i32 %204, %209
  %211 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %90, i64 %33
  %212 = load i8, ptr %211, align 1, !tbaa !7
  %213 = and i8 %212, 3
  %214 = icmp eq i8 %213, 1
  %215 = zext i1 %214 to i32
  %216 = add nuw nsw i32 %210, %215
  %217 = icmp eq i8 %51, 0
  br i1 %217, label %231, label %220

218:                                              ; preds = %47
  %219 = shl nuw nsw i64 %48, 1
  br label %237

220:                                              ; preds = %58
  %221 = add nsw i32 %216, -8
  %222 = icmp ult i32 %221, 5
  %223 = shl nuw nsw i64 %48, 1
  br i1 %222, label %229, label %224

224:                                              ; preds = %220
  %225 = add i8 %50, 8
  store i8 %225, ptr %49, align 1, !tbaa !7
  %226 = trunc i64 %223 to i32
  tail call void @simPutPixel(i32 noundef %226, i32 noundef %34, i32 noundef -35072) #4
  %227 = trunc i64 %223 to i32
  %228 = or disjoint i32 %227, 1
  tail call void @simPutPixel(i32 noundef %228, i32 noundef %34, i32 noundef -35072) #4
  tail call void @simPutPixel(i32 noundef %226, i32 noundef %36, i32 noundef -35072) #4
  br label %247

229:                                              ; preds = %220
  %230 = add i8 %50, 4
  store i8 %230, ptr %49, align 1, !tbaa !7
  br label %242

231:                                              ; preds = %58
  %232 = and i32 %216, -2
  %233 = icmp eq i32 %232, 6
  %234 = select i1 %233, i8 4, i8 0
  %235 = add i8 %234, %50
  store i8 %235, ptr %49, align 1, !tbaa !7
  %236 = shl nuw nsw i64 %48, 1
  br i1 %233, label %242, label %237

237:                                              ; preds = %231, %218
  %238 = phi i64 [ %219, %218 ], [ %236, %231 ]
  %239 = trunc i64 %238 to i32
  tail call void @simPutPixel(i32 noundef %239, i32 noundef %40, i32 noundef -8947849) #4
  %240 = trunc i64 %238 to i32
  %241 = or disjoint i32 %240, 1
  tail call void @simPutPixel(i32 noundef %241, i32 noundef %40, i32 noundef -8947849) #4
  tail call void @simPutPixel(i32 noundef %239, i32 noundef %42, i32 noundef -8947849) #4
  br label %247

242:                                              ; preds = %231, %229
  %243 = phi i64 [ %223, %229 ], [ %236, %231 ]
  %244 = trunc i64 %243 to i32
  tail call void @simPutPixel(i32 noundef %244, i32 noundef %37, i32 noundef -65536) #4
  %245 = trunc i64 %243 to i32
  %246 = or disjoint i32 %245, 1
  tail call void @simPutPixel(i32 noundef %246, i32 noundef %37, i32 noundef -65536) #4
  tail call void @simPutPixel(i32 noundef %244, i32 noundef %39, i32 noundef -65536) #4
  br label %247

247:                                              ; preds = %237, %242, %224, %52
  %248 = phi i32 [ %39, %242 ], [ %36, %224 ], [ %24, %52 ], [ %42, %237 ]
  %249 = phi i32 [ %246, %242 ], [ %228, %224 ], [ %57, %52 ], [ %241, %237 ]
  %250 = phi i32 [ -65536, %242 ], [ -35072, %224 ], [ -256, %52 ], [ -8947849, %237 ]
  tail call void @simPutPixel(i32 noundef %249, i32 noundef %248, i32 noundef %250) #4
  %251 = add nuw nsw i64 %48, 1
  %252 = icmp eq i64 %251, 256
  br i1 %252, label %44, label %47, !llvm.loop !12

253:                                              ; preds = %43, %258
  %254 = phi i64 [ 0, %43 ], [ %259, %258 ]
  br label %261

255:                                              ; preds = %258
  %256 = add nuw nsw i32 %17, 1
  %257 = icmp eq i32 %256, 1000000000
  br i1 %257, label %18, label %16, !llvm.loop !13

258:                                              ; preds = %261
  %259 = add nuw nsw i64 %254, 1
  %260 = icmp eq i64 %259, 128
  br i1 %260, label %255, label %253, !llvm.loop !14

261:                                              ; preds = %261, %253
  %262 = phi i64 [ 0, %253 ], [ %278, %261 ]
  %263 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %262, i64 %254
  %264 = load i8, ptr %263, align 1, !tbaa !7
  %265 = ashr i8 %264, 2
  store i8 %265, ptr %263, align 1, !tbaa !7
  %266 = or disjoint i64 %262, 1
  %267 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %266, i64 %254
  %268 = load i8, ptr %267, align 1, !tbaa !7
  %269 = ashr i8 %268, 2
  store i8 %269, ptr %267, align 1, !tbaa !7
  %270 = or disjoint i64 %262, 2
  %271 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %270, i64 %254
  %272 = load i8, ptr %271, align 1, !tbaa !7
  %273 = ashr i8 %272, 2
  store i8 %273, ptr %271, align 1, !tbaa !7
  %274 = or disjoint i64 %262, 3
  %275 = getelementptr inbounds [256 x [128 x i8]], ptr %1, i64 0, i64 %274, i64 %254
  %276 = load i8, ptr %275, align 1, !tbaa !7
  %277 = ashr i8 %276, 2
  store i8 %277, ptr %275, align 1, !tbaa !7
  %278 = add nuw nsw i64 %262, 4
  %279 = icmp eq i64 %278, 256
  br i1 %279, label %258, label %261, !llvm.loop !15
}

declare void @simInit(...) local_unnamed_addr #2

; Function Attrs: mustprogress nocallback nofree nosync nounwind willreturn memory(argmem: readwrite)
declare void @llvm.lifetime.start.p0(i64 immarg, ptr nocapture) #3

declare i32 @simRand(...) local_unnamed_addr #2

; Function Attrs: mustprogress nocallback nofree nosync nounwind willreturn memory(argmem: readwrite)
declare void @llvm.lifetime.end.p0(i64 immarg, ptr nocapture) #3

declare void @simPutPixel(i32 noundef, i32 noundef, i32 noundef) local_unnamed_addr #2

declare void @simFlush(...) local_unnamed_addr #2

declare void @simExit(...) local_unnamed_addr #2

attributes #0 = { mustprogress nofree norecurse nosync nounwind sspstrong willreturn memory(none) uwtable "min-legal-vector-width"="0" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cmov,+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #1 = { nounwind sspstrong uwtable "min-legal-vector-width"="0" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cmov,+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #2 = { "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cmov,+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #3 = { mustprogress nocallback nofree nosync nounwind willreturn memory(argmem: readwrite) }
attributes #4 = { nounwind }

!llvm.module.flags = !{!0, !1, !2, !3}
!llvm.ident = !{!4}

!0 = !{i32 1, !"wchar_size", i32 4}
!1 = !{i32 8, !"PIC Level", i32 2}
!2 = !{i32 7, !"PIE Level", i32 2}
!3 = !{i32 7, !"uwtable", i32 2}
!4 = !{!"clang version 18.1.8"}
!5 = distinct !{!5, !6}
!6 = !{!"llvm.loop.mustprogress"}
!7 = !{!8, !8, i64 0}
!8 = !{!"omnipotent char", !9, i64 0}
!9 = !{!"Simple C/C++ TBAA"}
!10 = distinct !{!10, !6}
!11 = distinct !{!11, !6}
!12 = distinct !{!12, !6}
!13 = distinct !{!13, !6}
!14 = distinct !{!14, !6}
!15 = distinct !{!15, !6}
