#import "ObjCExceptionCatcher.h"

@implementation ObjCExceptionCatcher

+ (BOOL)tryBlock:(void (NS_NOESCAPE ^)(void))block error:(NSError * _Nullable * _Nullable)error {
    @try {
        block();
        return YES;
    }
    @catch (NSException *exception) {
        if (error) {
            *error = [NSError errorWithDomain:@"ObjCExceptionCatcher"
                                         code:1
                                     userInfo:@{
                NSLocalizedDescriptionKey: exception.reason ?: @"Unknown exception",
                @"ExceptionName": exception.name ?: @"Unknown",
                @"ExceptionReason": exception.reason ?: @"Unknown"
            }];
        }
        return NO;
    }
}

@end
