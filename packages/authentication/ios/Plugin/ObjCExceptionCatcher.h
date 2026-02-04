#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

/// Helper class to catch Objective-C exceptions from Swift.
/// Swift cannot catch NSException, so we need an ObjC bridge for exception handling.
@interface ObjCExceptionCatcher : NSObject

/// Executes a block and catches any Objective-C exceptions.
/// @param block The block to execute.
/// @param error On return, contains the error if an exception was thrown.
/// @return YES if the block executed successfully, NO if an exception was caught.
+ (BOOL)tryBlock:(void (NS_NOESCAPE ^)(void))block error:(NSError * _Nullable * _Nullable)error;

@end

NS_ASSUME_NONNULL_END
