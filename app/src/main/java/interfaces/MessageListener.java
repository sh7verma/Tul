package interfaces;

import model.ChatMessageModel;

/**
 * Created by applify on 8/9/2017.
 */

public interface MessageListener {

    public void onMessageAdded(ChatMessageModel chatMessageModel);

    public void onMessageChanged(ChatMessageModel chatMessageModel);

    public void onMessageDelete(ChatMessageModel chatMessageModel);

}
