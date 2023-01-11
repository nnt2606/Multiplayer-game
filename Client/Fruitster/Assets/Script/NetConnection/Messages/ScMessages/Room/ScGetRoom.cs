using System;
using System.Collections.Generic;
using System.Net;
using Script.Model;
using UnityEngine;

namespace Script.Messages.ScMessages
{
    [Serializable]
    public class ScGetRoom : ScMessage
    {
        public HttpStatusCode statusCode;
        public string roomID;
        public string roomName;
        public List<User> userList;

        public string roomMaster;
        public override void OnMessage(Session session)
        {
            if (statusCode == HttpStatusCode.OK)
            {
                UserProperties.UserRoom.RoomName = roomName;
                UserProperties.UserRoom.RoomID = roomID;
                UserProperties.UserRoom.Players = userList;
                UserProperties.UserRoom.RoomMasterID = roomMaster;
                SingletonDontDestroy.Instance.DoAction(() => RoomSceneScript.Instance.RefreshRoom());
            }
            else
            {
                Debug.Log(statusCode);
            }
        }
    }
}