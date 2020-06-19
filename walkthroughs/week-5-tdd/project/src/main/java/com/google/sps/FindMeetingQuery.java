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
import static java.lang.Math.toIntExact;


public final class FindMeetingQuery {

  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {    
    // not sure why this works!! but I have to cast the request 
    //of attendees as an int 
    Collection<String> attendeesCollection = request.getAttendees();
    Collection<String> optionalAttendeesCollection = request.getOptionalAttendees();

    List<TimeRange> badTimes = new ArrayList<TimeRange>();
    List<TimeRange> goodTimes = new ArrayList<TimeRange>();

    // get the duration of the event, haven't implemented yet
    long durationLong = request.getDuration();
    int duration = (int) durationLong;

    if(duration >= TimeRange.WHOLE_DAY.duration())
    {
        return Arrays.asList();
    }

    Set<String> participants = new HashSet<String>();
    if(!(request.getOptionalAttendees().isEmpty()))
    {   
        for(String attendee: optionalAttendeesCollection)
        {
            participants.add(attendee);
        }
    }

    for(String attendee: attendeesCollection)
    {
        participants.add(attendee);
    }



    // before you go any further, I have really bad runtime on here!
    // sorry! :(
    //getting each attendee

    for(Event thisMeeting: events) {
        // get the attendees for this meeting (all meeting participants)
        Set<String> amp = new HashSet<String>(thisMeeting.getAttendees());
        // for every attendee in this meeting
        for(String participant1: amp)
        {
            // for every attendee in the meeting we want
            for(String participant: participants)
            {
                // if the participant is in this meeting, add it 
                // to the bad times we collect
                if(participant == participant1)
                {
                    // if there is a participant who has a meeting that's the whole day,
                    // then there will be no good meeting times 
                    if((thisMeeting.getWhen().equals(TimeRange.WHOLE_DAY)))
                    {
                        return Arrays.asList();
                    }
                    badTimes.add(thisMeeting.getWhen());
                }
            }
        }
    }

    //if there are no bad times, return the whole day
    if(badTimes.size() == 0)
    {
        return Arrays.asList(TimeRange.WHOLE_DAY);
    }

    // if there's one event, schedule good times 
    // before and after
    if(badTimes.size() == 1)
    {
        goodTimes.add(TimeRange.fromStartEnd(TimeRange.START_OF_DAY, badTimes.get(0).start(), false));
        goodTimes.add(TimeRange.fromStartEnd(badTimes.get(0).end(), TimeRange.END_OF_DAY, true));
        return goodTimes;
    }

    //sort all  meeting (bad) times after we collect them
    Collections.sort(badTimes, TimeRange.ORDER_BY_START);

    // if we can fit a meeting in before the beginning of the first one
    // schedule it 
    if(Math.abs(TimeRange.START_OF_DAY - badTimes.get(0).start()) > duration)
    {
        goodTimes.add(TimeRange.fromStartEnd(TimeRange.START_OF_DAY, badTimes.get(0).start(), false));
    }
    
    /*thought this variable name  was kinda funny!! 
    *im sure it's prolly too long but 
    * wanted to keep it :)
    */
    int endOfBadTimes = badTimes.size() - 1;

    for(int i = 0; i < badTimes.size() - 1; i++)
    {
        // if the meeting contains the next one, then just schedule before and after
        if(badTimes.get(i).contains(badTimes.get(i+1)) && i == 0)
        {
            goodTimes.add(TimeRange.fromStartEnd(badTimes.get(0).end(), TimeRange.END_OF_DAY, true));
            return goodTimes;
        }
        // if there's time between the next meeting and the one right now, 
        //schedule
        if((badTimes.get(i+1).start() >= badTimes.get(i).end() &&
        duration <= Math.abs(badTimes.get(i+1).start() - badTimes.get(i).end()))) 
        {
            goodTimes.add(TimeRange.fromStartEnd(badTimes.get(i).end(), badTimes.get(i+1).start(), false));
        } 
    }
    // if you're the end of all the meetings, and there's time
    // before the end of the day, add a meeting
    if((Math.abs(TimeRange.END_OF_DAY - badTimes.get(endOfBadTimes).end()) > duration))
    {
        goodTimes.add(TimeRange.fromStartEnd(badTimes.get(endOfBadTimes).end(), TimeRange.END_OF_DAY, true));
    }
    return goodTimes;
    }
        
}
