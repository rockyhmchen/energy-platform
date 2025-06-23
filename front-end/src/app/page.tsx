'use client'

import { useEffect, useState } from "react";
import { Card, CardContent } from "@/components/ui/card";
import { Line } from "react-chartjs-2";
import {
  Chart as ChartJS,
  LineElement,
  CategoryScale,
  LinearScale,
  PointElement,
  Tooltip,
  Legend,
} from "chart.js";

ChartJS.register(LineElement, CategoryScale, LinearScale, PointElement, Tooltip, Legend);

export default function Home() {
  const [summary, setSummary] = useState({});
  const [historical, setHistorical] = useState([]);
  const [isClient, setIsClient] = useState(false);

  useEffect(() => {
    setIsClient(true);
    setSummary({
      "totalProductionKwh": 3.8,
      "totalConsumptionKwh": 2.4,
      "netBalanceKwh": 1.2,
      "weather": {
        "temperatureCelsius": 23.7,
        "cloudCoverDecimal": 11,
        "windSpeedInSecond": 25,
        "solarIrradianceWm2": 6.4
      },
      "correlation": {
        "solarIrradianceVsSolarProduction": -2.8,
        "temperatureVsEnergyConsumption": 3.2
      }
    });

    setHistorical([
      {
        "timestamp": "2025-01-01T00:00:00.000+0000",
        "productionKwh": 3.8,
        "consumptionKwh": 2.4,
        "netBalanceKwh": 1.2,
        "solarProduction": 2.1,
        "temperatureCelsius": 23.7,
        "solarIrradianceWm2": 6.4
      },
      {
        "timestamp": "2025-01-01T01:00:00.000+0000",
        "productionKwh": 1.8,
        "consumptionKwh": 3.4,
        "netBalanceKwh": 5.2,
        "solarProduction": 6.1,
        "temperatureCelsius": 24.1,
        "solarIrradianceWm2": 6.5
      }
    ])
    // fetch("/api/energy-summary")
    //   .then((res) => res.json())
    //   .then(setSummary);
    //
    // fetch("/api/historical-data")
    //   .then((res) => res.json())
    //   .then(setHistorical);
  }, [])

  if (!summary) return <div className="p-8">Loading...</div>;

  return (
    <div className="p-8 space-y-8">
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        <Card>
          <CardContent className="p-4">
            <h2 className="text-xl font-semibold">Total Production</h2>
            <p>{summary?.totalProductionKwh} kWh</p>
          </CardContent>
        </Card>
        <Card>
          <CardContent className="p-4">
            <h2 className="text-xl font-semibold">Total Consumption</h2>
            <p>{summary?.totalConsumptionKwh} kWh</p>
          </CardContent>
        </Card>
        <Card>
          <CardContent className="p-4">
            <h2 className="text-xl font-semibold">Net Balance</h2>
            <p>{summary?.netBalanceKwh} kWh</p>
          </CardContent>
        </Card>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <Card>
          <CardContent className="p-4">
            <h2 className="text-xl font-semibold">Current Weather</h2>
            <ul className="space-y-1">
              <li>Temperature: {summary?.weather?.temperatureCelsius} °C</li>
              <li>Cloud Cover: {summary?.weather?.cloudCoverDecimal/100} %</li>
              <li>Wind Speed: {summary?.weather?.windSpeedInSecond} m/s</li>
              <li>Solar Irradiance: {summary?.weather?.solarIrradianceWm2} W/m²</li>
            </ul>
          </CardContent>
        </Card>
        <Card>
          <CardContent className="p-4">
            <h2 className="text-xl font-semibold">Correlation Metrics</h2>
            <ul className="space-y-1">
              <li>
                Solar vs Production: {summary?.correlation?.solarIrradianceVsSolarProduction} (
                {getStrength(summary?.correlation?.solarIrradianceVsSolarProduction)})
              </li>
              <li>
                Temp vs Consumption: {summary?.correlation?.temperatureVsEnergyConsumption} (
                {getStrength(summary?.correlation?.temperatureVsEnergyConsumption)})
              </li>
            </ul>
          </CardContent>
        </Card>
      </div>

      <div className="space-y-4">
        <h2 className="text-xl font-semibold">Net Balance Over Time</h2>
        <Line data={generateLineData(historical, "netBalanceKwh")} />

        <h2 className="text-xl font-semibold">Solar Irradiance vs Production</h2>
        <Line data={generateDualLineData(historical, "solarIrradianceWm2", "solarProduction")} />

        <h2 className="text-xl font-semibold">Temperature vs Consumption</h2>
        <Line data={generateDualLineData(historical, "temperatureCelsius", "consumptionKwh")} />
      </div>
    </div>
  );
}

function getStrength(value: number) {
  if (value > 0)return "Strong";
  if (value == 0) return "Moderate";
  if (value < 0) return "Weak";
  return "None";
}

function generateLineData(data, field) {
  return {
    labels: data.map((d) => d.timestamp),
    datasets: [
      {
        label: field.replace(/_/g, " "),
        data: data.map((d) => d[field]),
        fill: false,
        borderColor: "#3b82f6",
        tension: 0.4,
      },
    ],
  };
}

function generateDualLineData(data, field1, field2) {
  return {
    labels: data.map((d) => d.timestamp),
    datasets: [
      {
        label: field1.replace(/_/g, " "),
        data: data.map((d) => d[field1]),
        borderColor: "#10b981",
        fill: false,
        tension: 0.4,
      },
      {
        label: field2.replace(/_/g, " "),
        data: data.map((d) => d[field2]),
        borderColor: "#f59e0b",
        fill: false,
        tension: 0.4,
      },
    ],
  };
}
