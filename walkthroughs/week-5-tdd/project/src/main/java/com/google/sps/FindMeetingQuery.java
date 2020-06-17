// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.*;

public final class FindMeetingQuery {

  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {    
    List<TimeRange> goodTimes = new ArrayList<TimeRange>();

    // get the attendees of the meeting
    Collection<String> attendees = request.getAttendees();

    // get the duration of the event, haven't implemented yet
    long duration = request.getDuration();


        // get all events
        for(Event partMeeting: events) {
            Set<String> participants = new HashSet<String>(request.getAttendees());
            attendees.retainAll(partMeeting.getAttendees());
            if(attendees.isEmpty()  == false)   
            {
                TimeRange possibleTime = partMeeting.getWhen();
                for(TimeRange thisTime: goodTimes) {
                    if( !(possibleTime.contains(thisTime)))
                    {
                        goodTimes.add(possibleTime);
                    }
                }
            } 
        }
    return goodTimes;
  }
  // maps of all the times 
}
