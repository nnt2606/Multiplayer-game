using System;
using System.Net;

namespace Script.Messages.CsMessages.GamePlay
{
    [Serializable]
    public class CsUserObtainItem : CsMessage
    {
        public string itemID;

        public CsUserObtainItem( string itemId )
        {
            this.itemID = itemId;
        }

    }
}