using System;

namespace Script.Utils
{
    public class RandomUtils
    {
        private static Random random = new Random();
        
        public static float NextFloat()
        {
            double mantissa = (random.NextDouble() * 2.0) - 1.0;
            // choose -149 instead of -126 to also generate subnormal floats (*)
            double exponent = Math.Pow(2.0, random.Next(-126, 128));
            return (float)(mantissa * exponent);
        }

        public static bool NextBool()
        {
            return random.Next() > (Int32.MaxValue / 2);
        }
    }
}