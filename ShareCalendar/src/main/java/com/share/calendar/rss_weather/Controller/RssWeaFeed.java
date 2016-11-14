package com.share.calendar.rss_weather.Controller;

import java.util.ArrayList;
import java.util.List;

/*
 * Stores an RSS feed
 */
public class RssWeaFeed {

		final String hour;
		final String day;
		final String temp;
		final String tmx;
		final String tmn;
		final String sky;
		final String pty;
		final String pop;
		final String ws;
		final String wd;
		final String reh;
		final String r12;
		final String s12;
		final String r06;
		final String s06;
		final String wfKor;
		final String wfEn;
		final String wdKor;
		final String wdEn;
		final String pubDate;

        final List<RssWeaFeedMessage> entries = new ArrayList<RssWeaFeedMessage>();

        public RssWeaFeed(String hour, String day, String temp, String tmx, String tmn, String sky, String pty, String pop, 
                                String ws, String wd, String reh, String r12, String s12, String r06, String s06,
                                String wfKor, String wfEn, String wdKor, String wdEn, String pubDate) {
	        	this.hour = hour;
	        	this.day = day;
	        	this.temp = temp;
	        	this.tmx = tmx;
	        	this.tmn = tmn;
	        	this.sky = sky;
	        	this.pty = pty;
	        	this.pop = pop;
	        	this.ws = ws;
	        	this.wd = wd;
	        	this.reh = reh;
	        	this.r12 = r12;
	        	this.s12 = s12;
	        	this.r06 = r06;
	        	this.s06 = s06;
	        	this.wfKor = wfKor;
	        	this.wfEn = wfEn;
	        	this.wdKor = wdKor;
	        	this.wdEn = wdEn;
	        	this.pubDate = pubDate;
        }

        public List<RssWeaFeedMessage> getMessages() {
                return entries;
        }

        public String getHour() {
			return hour;
		}

		public String getDay() {
			return day;
		}

		public String getTemp() {
			return temp;
		}

		public String getTmx() {
			return tmx;
		}

		public String getTmn() {
			return tmn;
		}

		public String getSky() {
			return sky;
		}

		public String getPty() {
			return pty;
		}

		public String getPop() {
			return pop;
		}

		public String getWs() {
			return ws;
		}

		public String getWd() {
			return wd;
		}

		public String getReh() {
			return reh;
		}

		public String getR12() {
			return r12;
		}

		public String getS12() {
			return s12;
		}

		public String getR06() {
			return r06;
		}

		public String getS06() {
			return s06;
		}

		public String getWfKor() {
			return wfKor;
		}

		public String getWfEn() {
			return wfEn;
		}

		public String getWdKor() {
			return wdKor;
		}

		public String getWdEn() {
			return wdEn;
		}

		public String getPubDate() {
			return pubDate;
		}

		public List<RssWeaFeedMessage> getEntries() {
			return entries;
		}

		@Override
        public String toString() {
                return "Feed [hour=" + hour + ", day=" + day + ", temp=" + temp + 
                		", tmx=" + tmx + ", tmn=" + tmn + ", sky=" + sky + ", pty=" + pty + 
                		", pop=" + pop + ", ws=" + ws + ", wd=" + wd + ", reh=" + reh + 
                		", r12=" + r12 + ", s12=" + s12 + ", r06=" + r06 + ", s06=" + s06 + 
                		", wfKor=" + wfKor + ", wfEn=" + wfEn + ", wdKor=" + wdKor + ", wdEn=" + wdEn + 
                		", pubDate=" + pubDate + "]";
        }

}