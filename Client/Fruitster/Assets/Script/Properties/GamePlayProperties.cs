using UnityEngine;

namespace Script
{
    public class GamePlayProperties
    {
        public const int ChatBubbleExistTime = 3;
        // public static float Speed = 30f;
        public const float MaxDistance = 10f;
        public const long UdpInterval = 500;

        public const long TcpInterval = 500;
        
        public const int MaxMemberNumber = 4;

        public const float SlimeSlowDownConst = 2;
        public const int SlimeExistTime = 3;

        public const float MushroomSlowDownConst = 2;
        public static Color PoisonedColor = new Color32(212, 131, 205, 255);
        public const int MushroomPoisonTime = 5;

    }
}