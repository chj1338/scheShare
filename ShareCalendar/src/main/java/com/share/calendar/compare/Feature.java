package com.share.calendar.compare;

import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/*
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Scalar;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.highgui.Highgui;
*/
public class Feature {
	public void main(String[] args) throws SQLException {
//		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
/*		
		DBSQL db = new DBSQL();
		int count = db.getTotalCount();
		int idx = 0;
		System.out.println("start : " + currentDate());

		for(int i=1; i< count; i++) {
			ImageBean ib = db.getImageInfo(i);
			System.out.println(i + " done!");
			if(ib.getBlog_idx() != idx) {
				if(CompareImage(ib)) {
					idx = ib.getBlog_idx();
					db.updateBlogInfo(ib.getBlog_idx());
				}				
			}
			File file = new File(ib.getUrl());
			file.delete();
		}
*/		
		System.out.println("end : " + currentDate());
	}
	
	public static String currentDate() {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		String formattedDate = dateFormat.format(date);
		return formattedDate;
	}
	
	/*
	 *  ImageBean에는 비교할 이미지의 정보가 들어있음.
	 */
/*	
	public static boolean CompareImage(ImageBean ib) {
		Mat largeImage = Highgui.imread(ib.getUrl());
		
		if(largeImage.empty()) return false;
		
		Mat smallImage = Highgui.imread("C:\\03.jpg");	// 찾아낼 이미지
		
		FeatureDetector fd = FeatureDetector.create(FeatureDetector.SIFT); 
		
		final MatOfKeyPoint keyPointsLarge = new MatOfKeyPoint();
		final MatOfKeyPoint keyPointsSmall = new MatOfKeyPoint();
		
		fd.detect(largeImage, keyPointsLarge);
		fd.detect(smallImage, keyPointsSmall);
		
		Mat descriptorsLarge = new Mat();
		Mat descriptorsSmall = new Mat();
		
		DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.SIFT);
		extractor.compute(largeImage,  keyPointsLarge,  descriptorsLarge);
		extractor.compute(smallImage,  keyPointsSmall,  descriptorsSmall);
		
		MatOfDMatch matches = new MatOfDMatch();
		
		DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED); 		
		matcher.match(descriptorsLarge, descriptorsSmall, matches);
		
		MatOfDMatch matchesFiltered = new MatOfDMatch();
		
		List<DMatch> matchesList = matches.toList();
		List<DMatch> bestMatches = new ArrayList<DMatch>();
		Double max_dist = 0.0;
		Double min_dist = 100.0;
		
		for(int i=0; i<descriptorsLarge.rows(); i++) {
			double dist = matchesList.get(i).distance;
			if( dist < min_dist ) min_dist = dist;
			if( dist > max_dist ) max_dist = dist;
		}
		
		for(int i=0; i<descriptorsLarge.rows(); i++) {
			if( matchesList.get(i).distance <= max(2*min_dist, 0.02) ) {
				bestMatches.add(matches.toList().get(i));
			}
		}
		
		matchesFiltered.fromList(bestMatches);
		
		if(matchesFiltered.rows() >= 1) {
			Mat outImage = new Mat();
			MatOfByte matchesMask = new MatOfByte();
			Features2d.drawMatches(largeImage, keyPointsLarge, smallImage, keyPointsSmall, matchesFiltered, outImage, Scalar.all(-1), Scalar.all(-1), matchesMask, Features2d.NOT_DRAW_SINGLE_POINTS);
			Highgui.imwrite("c:\\temp\\" + ib.getIdx() + ".jpg", outImage); // 결과 이미지
			
			return true;
		} else {
			return false;
		}
	}
*/
	
	private static float max(double d, double e) {
		double max = 0;
		if(d > e) max = d;
		else max = e;
		return (float)max;
	}
	
}