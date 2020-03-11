//
//  TextRecognition.swift
//  MyFirebase
//
//  Created by Appinventiv on 06/03/20.
//  Copyright © 2020 Facebook. All rights reserved.
//

import Foundation
import Firebase
import UIKit
@objc(TextRecognition)
class TextRecognition: NSObject {
  private var count = 0
  @objc
  func constantsToExport() -> [AnyHashable : Any]! {
    return ["initialCount": 0]
  }
  @objc
  func increment() {
    count += 1
    print("count is \(count)")
  }
  
  @objc
  static func requiresMainQueueSetup() -> Bool {
    return true
  }
  @objc
  func getCount(_ callback: RCTResponseSenderBlock) {
    callback([count])
  }
  @objc
  func decrement(
    _ resolve: RCTPromiseResolveBlock,
    rejecter reject: RCTPromiseRejectBlock
    ) -> Void {
    if (count == 0) {
      let error = NSError(domain: "", code: 200, userInfo: nil)
      reject("E_COUNT", "count cannot be negative", error)
    } else {
      count -= 1
      resolve("count was decremented")
    }
  }
  
  let vision =  Vision.vision();
  
  
  @objc
  func imageFromPath(_ url:String,callback: @escaping
   RCTResponseSenderBlock
    ) -> Void{
    print("url",url)
    
    guard let info = url as? String else {
      return
    }
    
    
    load(fileName: info as String, completion: {
      
      result in
      
      let image  = VisionImage(image: result)
      let textRecognizer = self.vision.onDeviceTextRecognizer()
      
      textRecognizer.process(image) { result, error in
        guard error == nil, let result = result else {
          // ...
          return
        }
        
        // Recognized text
        
        callback([result.text])
      }
      
    })
  }
  
  
 @objc
   func load(fileName: String, completion: @escaping (UIImage)->()) {
    DispatchQueue.global(qos: .background).async {
      do {
        let data = try Data.init(contentsOf: URL.init(string:fileName)!)
        DispatchQueue.main.async {
          completion(UIImage(data: data)!)
        }
      }
      catch {
        print("Not able to load image")
        
      }
      
    }
    
  }
  
  
}
