package hr.foi.air.osobnibankar.grafovi;

import org.afree.chart.AFreeChart;
import org.afree.graphics.geom.Dimension;
import org.afree.graphics.geom.RectShape;
import org.afree.ui.RectangleInsets;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class GrafView extends View {

	public static final int DEFAULT_MINIMUM_DRAW_WIDTH = 0;
	public static final int DEFAULT_MINIMUM_DRAW_HEIGHT = 0;
	public static final int DEFAULT_MAXIMUM_DRAW_WIDTH = 2000;
	public static final int DEFAULT_MAXIMUM_DRAW_HEIGHT = 2000;
	Dimension size = null;
	private AFreeChart chart;
	
	private RectangleInsets insets = null;

	private int minimumDrawWidth;
	private int minimumDrawHeight;
	private int maximumDrawWidth;
	private int maximumDrawHeight;
	
	public GrafView(Context context) {
		super(context);
		this.initialize();
	}

	private void initialize() {

		this.minimumDrawWidth = DEFAULT_MINIMUM_DRAW_WIDTH;
		this.minimumDrawHeight = DEFAULT_MINIMUM_DRAW_HEIGHT;
		this.maximumDrawWidth = DEFAULT_MAXIMUM_DRAW_WIDTH;
		this.maximumDrawHeight = DEFAULT_MAXIMUM_DRAW_HEIGHT;
	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		this.insets = new RectangleInsets(0, 0, 0, 0);
		this.size = new Dimension(w, h);
	}
	
	public void setChart(AFreeChart chart) {

		this.chart = chart;
	}
	
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paintComponent(canvas);
	}

	public void paintComponent(Canvas canvas) {

		RectShape available = new RectShape(insets.getLeft(), insets.getTop(),
				size.getWidth() - insets.getLeft() - insets.getRight(),
				size.getHeight() - insets.getTop() - insets.getBottom());

		double drawWidth = available.getWidth();
		double drawHeight = available.getHeight();

		if (drawWidth < this.minimumDrawWidth) {
			drawWidth = this.minimumDrawWidth;
		} else if (drawWidth > this.maximumDrawWidth) {
			drawWidth = this.maximumDrawWidth;
		}

		if (drawHeight < this.minimumDrawHeight) {
			drawHeight = this.minimumDrawHeight;
		} else if (drawHeight > this.maximumDrawHeight) {
			drawHeight = this.maximumDrawHeight;
		}

		RectShape chartArea = new RectShape(0.0, 0.0, drawWidth, drawHeight);

		this.chart.draw(canvas, chartArea);
	}

	public AFreeChart getChart() {
		return this.chart;
	}

}