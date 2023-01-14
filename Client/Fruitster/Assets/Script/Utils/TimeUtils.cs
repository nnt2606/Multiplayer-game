using System;

namespace Script.Utils
{
    public static class TimeUtils
    {
        private static readonly DateTime Jan1St1970 = new DateTime
            (1970, 1, 1, 0, 0, 0, DateTimeKind.Utc);

        public static long CurrentTimeMillis()
        {
            return (long) (DateTime.UtcNow - Jan1St1970).TotalMilliseconds;
        }
    }
}