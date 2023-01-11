using System;

namespace Script.Model
{
    [Serializable]
    public class User
    {
        public string userID;
        public string userName;
        public string avatar = "Bunny";

        public User()
        {
            
        }

        protected bool Equals(User other)
        {
            return userID == other.userID && userName == other.userName;
        }

        public override bool Equals(object obj)
        {
            if (ReferenceEquals(null, obj)) return false;
            if (ReferenceEquals(this, obj)) return true;
            if (obj.GetType() != this.GetType()) return false;
            return Equals((User)obj);
        }

        public override int GetHashCode()
        {
            unchecked
            {
                return ((userID != null ? userID.GetHashCode() : 0) * 397) ^ (userName != null ? userName.GetHashCode() : 0);
            }
        }
    }
}