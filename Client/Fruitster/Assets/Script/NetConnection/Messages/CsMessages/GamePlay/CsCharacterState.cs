using System;
using UnityEngine;

namespace Script.Messages.CsMessages
{
    [Serializable]
    public class CsCharacterState : CsMessage
    {
        public string id;
        public float locationX;
        public float locationY;
        public float directionX;
        public float directionY;
        public ObjectState state;

        public CsCharacterState(string id, Vector2 location, Vector2 direction, ObjectState state)
        {
            this.id = id;
            this.locationX = location.x;
            this.locationY = location.y;
            this.directionX = direction.x;
            this.directionY = direction.y;
            this.state = state;
        }
    }
}