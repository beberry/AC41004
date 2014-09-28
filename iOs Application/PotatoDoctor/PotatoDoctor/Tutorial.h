//
//  Tutorial.h
//  PotatoDoctor
//
//  Created by Jekabs Stikans on 28/09/2014.
//  Copyright (c) 2014 Jekabs Stikans. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>


@interface Tutorial : NSManagedObject

@property (nonatomic, retain) NSString * descriptionText;
@property (nonatomic, retain) NSString * name;
@property (nonatomic, retain) NSString * videoName;

@end
