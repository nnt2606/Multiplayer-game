using System;

namespace Script.Messages.CsMessages
{
    [Serializable]
    public class CsCreateRoom : CsMessage
    {
        public string roomName;
        public string password;

        public CsCreateRoom(string roomName, string password)
        {
            this.roomName = roomName;
            this.password = password;
        }
    }
}