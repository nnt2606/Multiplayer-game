using System;

namespace Script.Messages.CsMessages
{
    [Serializable]
    public class CsChangeAvatar : CsMessage
    {
        public string avatar;

        public CsChangeAvatar(string avatar)
        {
            this.avatar = avatar;
        }
    }
}