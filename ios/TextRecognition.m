//
//  TextRecognition.m
//  MyFirebase
//
//  Created by Appinventiv on 06/03/20.
//  Copyright © 2020 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "React/RCTBridgeModule.h"
@interface RCT_EXTERN_MODULE(TextRecognition, NSObject)
RCT_EXTERN_METHOD(increment)
RCT_EXTERN_METHOD(getCount: (RCTResponseSenderBlock)callback)
RCT_EXTERN_METHOD(
                  decrement: (RCTPromiseResolveBlock)resolve
                  rejecter: (RCTPromiseRejectBlock)reject
                  )
//RCT_EXTERN_METHOD(imageFromPath :(NSDictionary*)url
//                                  callback:(RCTResponseSenderBlock))
RCT_EXTERN_METHOD(
                  imageFromPath:(NSString*) url callback:(RCTResponseSenderBlock)
)

@end
