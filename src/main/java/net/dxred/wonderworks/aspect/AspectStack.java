package net.dxred.wonderworks.aspect;

public class AspectStack {
	private Aspect aspect;
	private int count;

	public AspectStack(Aspect pAspect, int pCount) {
		this.aspect = pAspect;
		this.count = pCount;
	}

	public Aspect getAspect() {
		return this.aspect;
	}

	public int getCount() {
		return this.count;
	}

	public void setAspect(Aspect pAspect) {
		this.aspect = pAspect;
	}

	public void setCount(int pCount) {
		this.count = pCount;
	}
}
