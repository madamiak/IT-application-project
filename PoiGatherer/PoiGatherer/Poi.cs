using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PoiGatherer
{
    class Poi
    {
        public Int32 Id { set; get; }

        private String category = "";
        public String Category
        {
            get { return category; }
            set { category = Safe(value); }
        }
        private String name = "";
        public String Name
        {
            get { return name; }
            set { name = Safe(value); }
        }
        private String address = "";
        public String Address
        {
            get { return address; }
            set { address = Safe(value); }
        }
        private String description = "";
        public String Description
        {
            get { return description; }
            set { description = Safe(value); }
        }
        private String lat = "";
        public String Lat
        {
            get { return lat; }
            set { lat = Safe(value); }
        }
        private String lng = "";
        public String Lng
        {
            get { return lng; }
            set { lng = Safe(value); }
        }

        private String Safe(String value)
        {
            return value.Replace(";", ",").Replace("\"", "'").Replace("<br>", " ").Replace("  ", " ").Replace("\n", " ").Replace("\r", " ").Trim();
        }

        public override string ToString()
        {
            return "\"" + Id + "\";\"" + Category + "\";\"" + Name + "\";\"" + Address + "\";\"" + Description + "\";\"" + Lat + "\";\"" + Lng + "\"";
        }

        public Boolean IsEmpty()
        {
            return (Category == "");
        }

    }
}
