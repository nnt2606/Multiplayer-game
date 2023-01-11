using System;
using Script.Character;
using UnityEngine;

namespace Script.Messages.ScMessages
{
    [Serializable]
    public class ScCharacterState : ScMessage
    {
        public string id;
        public float locationX;
        public float locationY;
        public float directionX;
        public float directionY;
        public ObjectState state;
        public override void OnMessage(Session session)
        {
            if (id != UserProperties.MainPlayer.userID)
            {
                GameObject go = PlaySceneScript.Instance.GetPlayerById(id);
                ObjectUpdateMoving objectUpdateMoving = go.GetComponent<ObjectUpdateMoving>();
                if (objectUpdateMoving != null)
                {
                    objectUpdateMoving.location = new Vector3(locationX, locationY,objectUpdateMoving.location.z);
                    objectUpdateMoving.direction = new Vector3(directionX, directionY,objectUpdateMoving.location.z);
                    go.GetComponent<ObjectScript>().state = state;
                }

            } 
        }
    }
}