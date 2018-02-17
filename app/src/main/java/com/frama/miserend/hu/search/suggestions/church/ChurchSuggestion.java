package com.frama.miserend.hu.search.suggestions.church;

import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.search.suggestions.Suggestion;

/**
 * Created by Balazs on 2018. 02. 17..
 */

public class ChurchSuggestion extends Suggestion<Church> {

    public ChurchSuggestion(Church data) {
        super(data);
    }
}
