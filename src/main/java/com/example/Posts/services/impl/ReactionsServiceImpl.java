package com.example.Posts.services.impl;

import com.example.Posts.dto.ValidationStatus;
import com.example.Posts.entity.Reactions;
import com.example.Posts.repository.ReactionRepository;
import com.example.Posts.services.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.RecursiveAction;

@Service
public class ReactionsServiceImpl implements ReactionService {

    @Autowired
    ReactionRepository reactionRepository;

    @Override
    public Integer getReactionStatus(String postId, String userId) {
        Optional<Reactions> reactionsCheck=reactionRepository.findById(postId);
        if(reactionsCheck.isPresent()){
            Reactions reactions=reactionsCheck.get();
            if(reactions.getReactions().containsKey(userId)){
                return reactions.getReactions().get(userId);
            }
        }
        return -1;
    }

    @Override
    public ValidationStatus putReactionForPost(String postId,String userId,int reactionType) {
        Optional<Reactions> reactionsCheck=reactionRepository.findById(postId);
        ValidationStatus validationStatus=new ValidationStatus();
        if(!reactionsCheck.isPresent()){
            Reactions reactions1=new Reactions();
            reactions1.setPostId(postId);
            Map<String,Integer> hashMap=new HashMap<String,Integer>();
            hashMap.put(userId,reactionType);
            reactions1.setReactions(hashMap);
            reactionRepository.save(reactions1);
            validationStatus.setIsvalid(true);
        }
        else{
            Reactions reactions1=reactionsCheck.get();
            reactions1.getReactions().put(userId,reactionType);
            reactionRepository.save(reactions1);
            validationStatus.setIsvalid(true);
        }
        //reactionRepository.save(reactions);
        return validationStatus;
    }

    @Override
    public ValidationStatus removeReactionForPost(String postId, String userId) {
        Optional<Reactions> reactionsCheck = reactionRepository.findById(postId);
        ValidationStatus validationStatus=new ValidationStatus();
        validationStatus.setIsvalid(false);
        if(reactionsCheck.isPresent()==true){
            Reactions reactions=reactionsCheck.get();
            if(reactions.getReactions().containsKey(userId)){
                reactions.getReactions().remove(userId);
                reactionRepository.save(reactions);
                validationStatus.setIsvalid(true);
            }
        }
        return validationStatus;
    }

    @Override
    public Map<Integer,Integer> getReactionCountForPost(String postId) {
        Optional<Reactions> reactionsCheck= reactionRepository.findById(postId);
        HashMap<Integer,Integer> itemCount=new HashMap<Integer, Integer>();
        for(int i=1;i<7;i++){
            itemCount.put(i,0);
        }
        if(reactionsCheck.isPresent()){
            Reactions reactions=reactionsCheck.get();
            for (int i:reactions.getReactions().values()) {
                itemCount.put(i,itemCount.get(i)+1);
            }
            //return itemCount;
        }
        return itemCount;
    }
}
