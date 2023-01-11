using System.Collections.Generic;

namespace Script.Model
{
    public class Room
    {
        public string RoomID { get; set; }
        public string RoomName{ get; set; }
        public bool HasPass{ get; set; }
        public List<User> Players{ get; set; }
        public string RoomMasterID { get; set; }
    }
}