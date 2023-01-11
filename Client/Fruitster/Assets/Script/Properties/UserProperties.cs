using System.Collections.Generic;
using System.Net;
using System.Runtime.CompilerServices;
using Script.Character;
using Script.Model;

namespace Script
{
    public class UserProperties
    {
        public static bool LoginFailed = false;
        public static HttpStatusCode LoginStatusCode;
        public static bool RegisterFailed = false;
        public static HttpStatusCode RegisterStatusCode;

        public static AvatarSet UserAvatarSet = AvatarSetManager.Instance.GetAsset("Bunny");

        private static User _mainPlayer;
        public static User MainPlayer
        {
            get
            {
                if (_mainPlayer == null)
                {
                    _mainPlayer = new User();
                }

                return _mainPlayer;
            }
        }

        private static Room _userRoom;
        public static Room UserRoom
        {
            [MethodImpl(MethodImplOptions.Synchronized)]
            get
            {
                if (_userRoom == null)
                {
                    _userRoom = new Room();
                }

                return _userRoom;
            }
            [MethodImpl(MethodImplOptions.Synchronized)]
            set
            {
                _userRoom = value;
            }
        }
    }
}