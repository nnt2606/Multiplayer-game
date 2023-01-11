using System;

namespace Script.Messages.CsMessages
{
    [Serializable]
    public class CsGetRoomList : CsMessage
    {
        public int number;

        public CsGetRoomList(int number)
        {
            this.number = number;
        }
    }
}