package interfaces;

import model.ChatProfileModel;

/**
 * Created by applify on 8/18/2017.
 */

public interface YouChooseProfileListener {
    public void onChildChanged(ChatProfileModel chatProfileModel);
    public void onChildAdded(ChatProfileModel chatProfileModel);
}

