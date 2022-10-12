public class PhysicsTracker
{
    private double xVelocity;
    private double yVelocity;


    public void addXVelocityWithLimit(double xVelocityModifier, double maxMagnitude)
    {
        this.xVelocity += xVelocityModifier;
        clampXVelocity(maxMagnitude);
    }

    public void addYVelocityWithLimit(double yVelocity, double maxMagnitude)
    {
        this.yVelocity += yVelocity;
        clampYVelocity(maxMagnitude);
    }

    public void slowXVelocity(double slowDownMultiplier)
    {
        xVelocity *=slowDownMultiplier;
        if (Math.abs(xVelocity) < 0.05)
            xVelocity = 0.0;
    }

    public void clampXVelocity(double maxXVelocity)
    {
        if (this.xVelocity < -maxXVelocity)
            this.xVelocity = -maxXVelocity;
        if (this.xVelocity > maxXVelocity)
            this.xVelocity = maxXVelocity;
    }

    public void clampYVelocity(double maxYVelocity)
    {
        if (this.yVelocity < -maxYVelocity)
            this.yVelocity = -maxYVelocity;
        if (this.yVelocity > maxYVelocity)
            this.yVelocity = maxYVelocity;
    }

    public double getXVelocity()
    {
        return xVelocity;
    }

    public double getYVelocity()
    {
        return yVelocity;
    }

    public void setYVelocityZero()
    {
        this.yVelocity = 0.0;
    }
}
