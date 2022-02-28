package com.inyoon.bookkureomi.auction;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Scheduled(cron="* * * * * *")
    public void auctionSchedule() {
        //auction update
        //get auction list (status = open, endDate < Sysdate)
        //for(auction a : auctionList)
            //bid done
            //bid -> order
            //auction update (state : open -> close)

        //end
    }
}
