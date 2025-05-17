; ModuleID = '../app/app.c'
source_filename = "../app/app.c"
target datalayout = "e-m:e-p270:32:32-p271:32:32-p272:64:64-i64:64-i128:128-f80:128-n8:16:32:64-S128"
target triple = "x86_64-pc-linux-gnu"

@coeff = dso_local local_unnamed_addr constant i32 2, align 4
@switch.table.app = private unnamed_addr constant [4 x i32] [i32 -8947849, i32 -65536, i32 -35072, i32 -256], align 4

; Function Attrs: mustprogress nofree norecurse nosync nounwind sspstrong willreturn memory(none) uwtable
define dso_local range(i32 -16776961, -255) i32 @color(i32 noundef %0) local_unnamed_addr #0 {
  %2 = icmp ult i32 %0, 4
  br i1 %2, label %3, label %7

3:                                                ; preds = %1
  %4 = zext nneg i32 %0 to i64
  %5 = getelementptr inbounds [4 x i32], ptr @switch.table.app, i64 0, i64 %4
  %6 = load i32, ptr %5, align 4
  br label %7

7:                                                ; preds = %1, %3
  %8 = phi i32 [ %6, %3 ], [ -16776961, %1 ]
  ret i32 %8
}

; Function Attrs: nounwind sspstrong uwtable
define dso_local void @app() local_unnamed_addr #1 {
  %1 = alloca [32768 x i8], align 16
  br label %2

2:                                                ; preds = %0, %5
  %3 = phi i64 [ 0, %0 ], [ %6, %5 ]
  %4 = getelementptr inbounds i8, ptr %1, i64 %3
  br label %8

5:                                                ; preds = %8
  %6 = add nuw nsw i64 %3, 1
  %7 = icmp eq i64 %6, 128
  br i1 %7, label %18, label %2, !llvm.loop !5

8:                                                ; preds = %2, %8
  %9 = phi i64 [ 0, %2 ], [ %16, %8 ]
  %10 = tail call i32 (...) @simRand() #3
  %11 = lshr i32 %10, 8
  %12 = trunc i32 %11 to i8
  %13 = and i8 %12, 3
  %14 = shl nuw nsw i64 %9, 7
  %15 = getelementptr inbounds i8, ptr %4, i64 %14
  store i8 %13, ptr %15, align 1, !tbaa !8
  %16 = add nuw nsw i64 %9, 1
  %17 = icmp eq i64 %16, 256
  br i1 %17, label %5, label %8, !llvm.loop !11

18:                                               ; preds = %5, %102
  %19 = phi i32 [ %103, %102 ], [ 0, %5 ]
  br label %21

20:                                               ; preds = %102
  ret void

21:                                               ; preds = %18, %28
  %22 = phi i64 [ 0, %18 ], [ %29, %28 ]
  %23 = getelementptr inbounds i8, ptr %1, i64 %22
  %24 = trunc i64 %22 to i32
  %25 = trunc i64 %22 to i32
  %26 = shl i32 %25, 1
  br label %31

27:                                               ; preds = %28
  tail call void (...) @simFlush() #3
  br label %99

28:                                               ; preds = %88
  %29 = add nuw nsw i64 %22, 1
  %30 = icmp eq i64 %29, 128
  br i1 %30, label %27, label %21, !llvm.loop !12

31:                                               ; preds = %21, %88
  %32 = phi i64 [ 0, %21 ], [ %89, %88 ]
  %33 = shl nuw nsw i64 %32, 7
  %34 = getelementptr inbounds i8, ptr %23, i64 %33
  %35 = load i8, ptr %34, align 1, !tbaa !8
  %36 = and i8 %35, 3
  switch i8 %36, label %37 [
    i8 2, label %75
    i8 3, label %39
  ]

37:                                               ; preds = %31
  %38 = trunc i64 %32 to i32
  br label %40

39:                                               ; preds = %31
  br label %75

40:                                               ; preds = %37, %49
  %41 = phi i32 [ %50, %49 ], [ -2, %37 ]
  %42 = phi i32 [ %64, %49 ], [ 0, %37 ]
  %43 = add i32 %41, %24
  %44 = and i32 %43, 255
  %45 = zext nneg i32 %44 to i64
  %46 = getelementptr i8, ptr %1, i64 %45
  br label %52

47:                                               ; preds = %49
  %48 = icmp eq i8 %36, 0
  br i1 %48, label %67, label %71

49:                                               ; preds = %52
  %50 = add nsw i32 %41, 1
  %51 = icmp eq i32 %50, 3
  br i1 %51, label %47, label %40, !llvm.loop !13

52:                                               ; preds = %40, %52
  %53 = phi i32 [ -2, %40 ], [ %65, %52 ]
  %54 = phi i32 [ %42, %40 ], [ %64, %52 ]
  %55 = add i32 %53, %38
  %56 = shl i32 %55, 7
  %57 = and i32 %56, 32640
  %58 = zext nneg i32 %57 to i64
  %59 = getelementptr i8, ptr %46, i64 %58
  %60 = load i8, ptr %59, align 1, !tbaa !8
  %61 = and i8 %60, 3
  %62 = icmp eq i8 %61, 1
  %63 = zext i1 %62 to i32
  %64 = add nsw i32 %54, %63
  %65 = add nsw i32 %53, 1
  %66 = icmp eq i32 %65, 3
  br i1 %66, label %49, label %52, !llvm.loop !14

67:                                               ; preds = %47
  %68 = and i32 %64, -2
  %69 = icmp eq i32 %68, 6
  %70 = zext i1 %69 to i32
  br label %75

71:                                               ; preds = %47
  %72 = add i32 %64, -8
  %73 = icmp ult i32 %72, 5
  %74 = select i1 %73, i32 1, i32 2
  br label %75

75:                                               ; preds = %67, %71, %31, %39
  %76 = phi i32 [ 0, %39 ], [ 3, %31 ], [ %70, %67 ], [ %74, %71 ]
  %77 = trunc nuw nsw i32 %76 to i8
  %78 = shl nuw nsw i8 %77, 2
  %79 = add i8 %78, %35
  store i8 %79, ptr %34, align 1, !tbaa !8
  %80 = trunc i64 %32 to i32
  %81 = shl i32 %80, 1
  %82 = zext nneg i32 %76 to i64
  %83 = getelementptr inbounds [4 x i32], ptr @switch.table.app, i64 0, i64 %82
  %84 = load i32, ptr %83, align 4
  br label %85

85:                                               ; preds = %75, %91
  %86 = phi i32 [ 0, %75 ], [ %92, %91 ]
  %87 = add nuw nsw i32 %86, %26
  br label %94

88:                                               ; preds = %91
  %89 = add nuw nsw i64 %32, 1
  %90 = icmp eq i64 %89, 256
  br i1 %90, label %28, label %31, !llvm.loop !15

91:                                               ; preds = %94
  %92 = add nuw nsw i32 %86, 1
  %93 = icmp eq i32 %86, 0
  br i1 %93, label %85, label %88, !llvm.loop !16

94:                                               ; preds = %85, %94
  %95 = phi i32 [ 0, %85 ], [ %97, %94 ]
  %96 = add nuw nsw i32 %95, %81
  tail call void @simPutPixel(i32 noundef %96, i32 noundef %87, i32 noundef %84) #3
  %97 = add nuw nsw i32 %95, 1
  %98 = icmp eq i32 %95, 0
  br i1 %98, label %94, label %91, !llvm.loop !17

99:                                               ; preds = %27, %105
  %100 = phi i64 [ 0, %27 ], [ %106, %105 ]
  %101 = getelementptr inbounds i8, ptr %1, i64 %100
  br label %108

102:                                              ; preds = %105
  %103 = add nuw nsw i32 %19, 1
  %104 = icmp eq i32 %103, 1000000000
  br i1 %104, label %20, label %18, !llvm.loop !18

105:                                              ; preds = %108
  %106 = add nuw nsw i64 %100, 1
  %107 = icmp eq i64 %106, 128
  br i1 %107, label %102, label %99, !llvm.loop !19

108:                                              ; preds = %99, %108
  %109 = phi i64 [ 0, %99 ], [ %114, %108 ]
  %110 = shl nuw nsw i64 %109, 7
  %111 = getelementptr inbounds i8, ptr %101, i64 %110
  %112 = load i8, ptr %111, align 1, !tbaa !8
  %113 = ashr i8 %112, 2
  store i8 %113, ptr %111, align 1, !tbaa !8
  %114 = add nuw nsw i64 %109, 1
  %115 = icmp eq i64 %114, 256
  br i1 %115, label %105, label %108, !llvm.loop !20
}

declare i32 @simRand(...) local_unnamed_addr #2

declare void @simPutPixel(i32 noundef, i32 noundef, i32 noundef) local_unnamed_addr #2

declare void @simFlush(...) local_unnamed_addr #2

attributes #0 = { mustprogress nofree norecurse nosync nounwind sspstrong willreturn memory(none) uwtable "min-legal-vector-width"="0" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cmov,+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #1 = { nounwind sspstrong uwtable "min-legal-vector-width"="0" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cmov,+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #2 = { "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cmov,+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #3 = { nounwind }

!llvm.module.flags = !{!0, !1, !2, !3}
!llvm.ident = !{!4}

!0 = !{i32 1, !"wchar_size", i32 4}
!1 = !{i32 8, !"PIC Level", i32 2}
!2 = !{i32 7, !"PIE Level", i32 2}
!3 = !{i32 7, !"uwtable", i32 2}
!4 = !{!"clang version 19.1.7"}
!5 = distinct !{!5, !6, !7}
!6 = !{!"llvm.loop.mustprogress"}
!7 = !{!"llvm.loop.unroll.disable"}
!8 = !{!9, !9, i64 0}
!9 = !{!"omnipotent char", !10, i64 0}
!10 = !{!"Simple C/C++ TBAA"}
!11 = distinct !{!11, !6, !7}
!12 = distinct !{!12, !6, !7}
!13 = distinct !{!13, !6, !7}
!14 = distinct !{!14, !6, !7}
!15 = distinct !{!15, !6, !7}
!16 = distinct !{!16, !6, !7}
!17 = distinct !{!17, !6, !7}
!18 = distinct !{!18, !6, !7}
!19 = distinct !{!19, !6, !7}
!20 = distinct !{!20, !6, !7}
