package com.example.chenweiming.mypanelapplication.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david.chen@soocii.me on 2018/6/4.
 */
public class GiftFactory {
    public static List<GiftSection> provideGiftSections(int numCatSize) {
        List<GiftSection> categories = new ArrayList<>();
        for (int index = 0; index < numCatSize; index ++) {
            GiftSection category = new GiftSection();
            category.categoryTitle = "Cat" + index;
            category.giftList = provideGifts(index + (index+1)*10);
            categories.add(category);
        }
        return categories;
    }

    public static List<Gift> provideGifts(int numSize) {
        List<Gift> gifts = new ArrayList<>();
        for (int index = 0; index < numSize; index ++) {
            Gift gift = new Gift();

            switch (index % 4) {
                case 0: {
                    gift.iconUrl = "https://png.icons8.com/ios/2x/power-over-ethernet-filled.png";
                    gift.text = "stop";
                    break;
                }
                case 1: {
                    gift.iconUrl = "https://png.icons8.com/doodle/2x/yelp.png";
                    gift.text = "yelp";
                    break;
                }
                case 2: {
                    gift.iconUrl = "https://png.icons8.com/doodle/2x/social-network-logo.png";
                    gift.text = "logo";
                    break;
                }
                case 3: {
                    gift.iconUrl = "https://png.icons8.com/doodle/2x/air-raider.png";
                    gift.text = "bomb";
                    break;
                }
            }
            gifts.add(gift);
        }
        return gifts;
    }
}
