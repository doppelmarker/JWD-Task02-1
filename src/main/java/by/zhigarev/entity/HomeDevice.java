package by.zhigarev.entity;

public class HomeDevice implements Appliance {
    double power_consumption;

    public HomeDevice(double power_consumption) {
        this.power_consumption = power_consumption;
    }

    public double getPower_consumption() {
        return power_consumption;
    }

    public void setPower_consumption(double power_consumption) {
        this.power_consumption = power_consumption;
    }

    @Override
    public String toString() {
        return "HomeDevice{" +
                "power_consumption=" + power_consumption +
                '}';
    }
}
