using System;
using System.Net;
using Script.Model;
using UnityEngine;
using UnityEngine.SceneManagement;

namespace Script.Messages.ScMessages
{
    [Serializable]
    public class ScJoinRoom : ScMessage
    {
        public User user;
        public HttpStatusCode statusCode;
        public override void OnMessage(Session session)
        {
            if (statusCode == HttpStatusCode.OK)
            {
                if (user.userID ==UserProperties.MainPlayer.userID)
                {
                    SingletonDontDestroy.Instance.DoAction(() =>SceneManager.LoadScene("RoomScene"));
                }
                else
                {
                    UserProperties.UserRoom.Players.Add(user);
                    SingletonDontDestroy.Instance.DoAction(() => RoomSceneScript.Instance.RefreshRoom());
                }
                
            } else
            {
                Debug.Log(statusCode);
            }
        }
    }
}