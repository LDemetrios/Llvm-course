; ModuleID = '../app/sim.c'
source_filename = "../app/sim.c"
target datalayout = "e-m:e-p270:32:32-p271:32:32-p272:64:64-i64:64-i128:128-f80:128-n8:16:32:64-S128"
target triple = "x86_64-pc-linux-gnu"

%union.SDL_Event = type { %struct.SDL_SensorEvent, [8 x i8] }
%struct.SDL_SensorEvent = type { i32, i32, i32, [6 x float], i64 }

@Window = internal global ptr null, align 8
@Renderer = internal global ptr null, align 8
@.str = private unnamed_addr constant [20 x i8] c"User-requested quit\00", align 1
@.str.1 = private unnamed_addr constant [60 x i8] c"SDL_TRUE != SDL_HasEvent(SDL_QUIT) && \22User-requested quit\22\00", align 1
@.str.2 = private unnamed_addr constant [13 x i8] c"../app/sim.c\00", align 1
@__PRETTY_FUNCTION__.simFlush = private unnamed_addr constant [16 x i8] c"void simFlush()\00", align 1
@Ticks = internal global i32 0, align 4
@.str.3 = private unnamed_addr constant [13 x i8] c"Out of range\00", align 1
@.str.4 = private unnamed_addr constant [43 x i8] c"0 <= x && x < SIM_X_SIZE && \22Out of range\22\00", align 1
@__PRETTY_FUNCTION__.simPutPixel = private unnamed_addr constant [32 x i8] c"void simPutPixel(int, int, int)\00", align 1
@.str.5 = private unnamed_addr constant [43 x i8] c"0 <= y && y < SIM_Y_SIZE && \22Out of range\22\00", align 1

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define dso_local void @simInit() #0 {
  %1 = call i32 @SDL_Init(i32 noundef 32)
  %2 = call i32 @SDL_CreateWindowAndRenderer(i32 noundef 512, i32 noundef 256, i32 noundef 0, ptr noundef @Window, ptr noundef @Renderer)
  %3 = load ptr, ptr @Renderer, align 8
  %4 = call i32 @SDL_SetRenderDrawColor(ptr noundef %3, i8 noundef zeroext 0, i8 noundef zeroext 0, i8 noundef zeroext 0, i8 noundef zeroext 0)
  %5 = load ptr, ptr @Renderer, align 8
  %6 = call i32 @SDL_RenderClear(ptr noundef %5)
  %7 = call i64 @time(ptr noundef null) #4
  %8 = trunc i64 %7 to i32
  call void @srand(i32 noundef %8) #4
  call void @simPutPixel(i32 noundef 0, i32 noundef 0, i32 noundef 0)
  call void @simFlush()
  ret void
}

declare i32 @SDL_Init(i32 noundef) #1

declare i32 @SDL_CreateWindowAndRenderer(i32 noundef, i32 noundef, i32 noundef, ptr noundef, ptr noundef) #1

declare i32 @SDL_SetRenderDrawColor(ptr noundef, i8 noundef zeroext, i8 noundef zeroext, i8 noundef zeroext, i8 noundef zeroext) #1

declare i32 @SDL_RenderClear(ptr noundef) #1

; Function Attrs: nounwind
declare void @srand(i32 noundef) #2

; Function Attrs: nounwind
declare i64 @time(ptr noundef) #2

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define dso_local void @simPutPixel(i32 noundef %0, i32 noundef %1, i32 noundef %2) #0 {
  %4 = alloca i32, align 4
  %5 = alloca i32, align 4
  %6 = alloca i32, align 4
  %7 = alloca i8, align 1
  %8 = alloca i8, align 1
  %9 = alloca i8, align 1
  %10 = alloca i8, align 1
  store i32 %0, ptr %4, align 4
  store i32 %1, ptr %5, align 4
  store i32 %2, ptr %6, align 4
  %11 = load i32, ptr %4, align 4
  %12 = icmp sle i32 0, %11
  br i1 %12, label %13, label %18

13:                                               ; preds = %3
  %14 = load i32, ptr %4, align 4
  %15 = icmp slt i32 %14, 512
  br i1 %15, label %16, label %18

16:                                               ; preds = %13
  br i1 true, label %17, label %18

17:                                               ; preds = %16
  br label %19

18:                                               ; preds = %16, %13, %3
  call void @__assert_fail(ptr noundef @.str.4, ptr noundef @.str.2, i32 noundef 51, ptr noundef @__PRETTY_FUNCTION__.simPutPixel) #5
  unreachable

19:                                               ; preds = %17
  %20 = load i32, ptr %5, align 4
  %21 = icmp sle i32 0, %20
  br i1 %21, label %22, label %27

22:                                               ; preds = %19
  %23 = load i32, ptr %5, align 4
  %24 = icmp slt i32 %23, 256
  br i1 %24, label %25, label %27

25:                                               ; preds = %22
  br i1 true, label %26, label %27

26:                                               ; preds = %25
  br label %28

27:                                               ; preds = %25, %22, %19
  call void @__assert_fail(ptr noundef @.str.5, ptr noundef @.str.2, i32 noundef 52, ptr noundef @__PRETTY_FUNCTION__.simPutPixel) #5
  unreachable

28:                                               ; preds = %26
  %29 = load i32, ptr %6, align 4
  %30 = ashr i32 %29, 24
  %31 = trunc i32 %30 to i8
  store i8 %31, ptr %7, align 1
  %32 = load i32, ptr %6, align 4
  %33 = ashr i32 %32, 16
  %34 = and i32 %33, 255
  %35 = trunc i32 %34 to i8
  store i8 %35, ptr %8, align 1
  %36 = load i32, ptr %6, align 4
  %37 = ashr i32 %36, 8
  %38 = and i32 %37, 255
  %39 = trunc i32 %38 to i8
  store i8 %39, ptr %9, align 1
  %40 = load i32, ptr %6, align 4
  %41 = and i32 %40, 255
  %42 = trunc i32 %41 to i8
  store i8 %42, ptr %10, align 1
  %43 = load ptr, ptr @Renderer, align 8
  %44 = load i8, ptr %8, align 1
  %45 = load i8, ptr %9, align 1
  %46 = load i8, ptr %10, align 1
  %47 = load i8, ptr %7, align 1
  %48 = call i32 @SDL_SetRenderDrawColor(ptr noundef %43, i8 noundef zeroext %44, i8 noundef zeroext %45, i8 noundef zeroext %46, i8 noundef zeroext %47)
  %49 = load ptr, ptr @Renderer, align 8
  %50 = load i32, ptr %4, align 4
  %51 = load i32, ptr %5, align 4
  %52 = call i32 @SDL_RenderDrawPoint(ptr noundef %49, i32 noundef %50, i32 noundef %51)
  %53 = call i32 @SDL_GetTicks()
  store i32 %53, ptr @Ticks, align 4
  ret void
}

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define dso_local void @simExit() #0 {
  %1 = alloca %union.SDL_Event, align 8
  br label %2

2:                                                ; preds = %0, %9
  %3 = call i32 @SDL_PollEvent(ptr noundef %1)
  %4 = icmp ne i32 %3, 0
  br i1 %4, label %5, label %9

5:                                                ; preds = %2
  %6 = load i32, ptr %1, align 8
  %7 = icmp eq i32 %6, 256
  br i1 %7, label %8, label %9

8:                                                ; preds = %5
  br label %10

9:                                                ; preds = %5, %2
  br label %2

10:                                               ; preds = %8
  %11 = load ptr, ptr @Renderer, align 8
  call void @SDL_DestroyRenderer(ptr noundef %11)
  %12 = load ptr, ptr @Window, align 8
  call void @SDL_DestroyWindow(ptr noundef %12)
  call void @SDL_Quit()
  ret void
}

declare i32 @SDL_PollEvent(ptr noundef) #1

declare void @SDL_DestroyRenderer(ptr noundef) #1

declare void @SDL_DestroyWindow(ptr noundef) #1

declare void @SDL_Quit() #1

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define dso_local void @simFlush() #0 {
  %1 = alloca i32, align 4
  call void @SDL_PumpEvents()
  %2 = call i32 @SDL_HasEvent(i32 noundef 256)
  %3 = icmp ne i32 1, %2
  br i1 %3, label %4, label %6

4:                                                ; preds = %0
  br i1 true, label %5, label %6

5:                                                ; preds = %4
  br label %7

6:                                                ; preds = %4, %0
  call void @__assert_fail(ptr noundef @.str.1, ptr noundef @.str.2, i32 noundef 40, ptr noundef @__PRETTY_FUNCTION__.simFlush) #5
  unreachable

7:                                                ; preds = %5
  %8 = call i32 @SDL_GetTicks()
  %9 = load i32, ptr @Ticks, align 4
  %10 = sub i32 %8, %9
  store i32 %10, ptr %1, align 4
  %11 = load i32, ptr %1, align 4
  %12 = icmp ult i32 %11, 50
  br i1 %12, label %13, label %16

13:                                               ; preds = %7
  %14 = load i32, ptr %1, align 4
  %15 = sub i32 50, %14
  call void @SDL_Delay(i32 noundef %15)
  br label %16

16:                                               ; preds = %13, %7
  %17 = load ptr, ptr @Renderer, align 8
  call void @SDL_RenderPresent(ptr noundef %17)
  ret void
}

declare void @SDL_PumpEvents() #1

declare i32 @SDL_HasEvent(i32 noundef) #1

; Function Attrs: noreturn nounwind
declare void @__assert_fail(ptr noundef, ptr noundef, i32 noundef, ptr noundef) #3

declare i32 @SDL_GetTicks() #1

declare void @SDL_Delay(i32 noundef) #1

declare void @SDL_RenderPresent(ptr noundef) #1

declare i32 @SDL_RenderDrawPoint(ptr noundef, i32 noundef, i32 noundef) #1

; Function Attrs: noinline nounwind optnone sspstrong uwtable
define dso_local i32 @simRand() #0 {
  %1 = call i32 @rand() #4
  ret i32 %1
}

; Function Attrs: nounwind
declare i32 @rand() #2

attributes #0 = { noinline nounwind optnone sspstrong uwtable "frame-pointer"="all" "min-legal-vector-width"="0" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cmov,+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #1 = { "frame-pointer"="all" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cmov,+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #2 = { nounwind "frame-pointer"="all" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cmov,+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #3 = { noreturn nounwind "frame-pointer"="all" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cmov,+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #4 = { nounwind }
attributes #5 = { noreturn nounwind }

!llvm.module.flags = !{!0, !1, !2, !3, !4}
!llvm.ident = !{!5}

!0 = !{i32 1, !"wchar_size", i32 4}
!1 = !{i32 8, !"PIC Level", i32 2}
!2 = !{i32 7, !"PIE Level", i32 2}
!3 = !{i32 7, !"uwtable", i32 2}
!4 = !{i32 7, !"frame-pointer", i32 2}
!5 = !{!"clang version 18.1.8"}
