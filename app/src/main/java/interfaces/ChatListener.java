package interfaces;

import model.ChatDialogModel;

/**
 * Created by applify on 8/4/2017.
 */

public interface ChatListener {

    public void onChildChanged(ChatDialogModel chatDialogModel);

    public void onChildDelete(ChatDialogModel chatDialogModel);

    public void onChildAdded(ChatDialogModel chatDialogModel);
}
