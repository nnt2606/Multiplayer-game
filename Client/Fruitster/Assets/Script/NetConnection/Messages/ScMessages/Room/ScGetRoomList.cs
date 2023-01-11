using System;
using System.Collections.Generic;
using Script.Model;

namespace Script.Messages.ScMessages
{
    [Serializable]
    public class ScGetRoomList : ScMessage
    {
        public List<SimpleRoom> roomList;

        public override void OnMessage(Session session)
        {
            RoomSelectSceneScript.Instance.rooms = roomList;
        }
    }
}