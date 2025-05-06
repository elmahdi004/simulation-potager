<script setup>
import { ref } from "vue";
import PlantList from "./components/PlantList.vue";
import PlantDetail from "./components/PlantDetail.vue";
import InsectList from "./components/InsectList.vue";
import InsectDetail from "./components/InsectDetail.vue";
import PlotGrid from "./components/PlotGrid.vue";
import TreatmentDeviceList from "./components/TreatmentDeviceList.vue";
import SimulationControl from "./components/SimulationControl.vue";
import SimulationStatus from "./components/SimulationStatus.vue";

// Placeholder data
const plants = ref([
  { id: 1, species: "Tomate", age: 3, isMature: false, plot: "A1" },
  { id: 2, species: "Carotte", age: 5, isMature: true, plot: "A2" },
]);
const insects = ref([
  {
    id: 1,
    species: "Coccinelle",
    sex: "Femelle",
    healthIndex: 8,
    mobility: "Haute",
    resistance: "Forte",
    plot: "A1",
  },
  {
    id: 2,
    species: "Puceron",
    sex: "Mâle",
    healthIndex: 4,
    mobility: "Moyenne",
    resistance: "Faible",
    plot: "A3",
  },
]);
const plots = ref([
  { id: "A1", plant: plants.value[0], insects: [insects.value[0]] },
  { id: "A2", plant: plants.value[1], insects: [] },
  { id: "A3", plant: null, insects: [insects.value[1]] },
]);
const devices = ref([
  {
    id: 1,
    type: "Arrosage",
    program: { start: "08:00", duration: 10, treatmentType: "Eau" },
    affectedPlots: ["A1", "A2"],
  },
]);
const simulationStatus = ref({
  step: 12,
  plants: plants.value.length,
  insects: insects.value.length,
  devices: devices.value.length,
  summary: "La simulation progresse normalement. Aucun incident détecté.",
});

const selectedPlant = ref(null);
const selectedInsect = ref(null);

function selectPlant(plant) {
  selectedPlant.value = plant;
}
function selectInsect(insect) {
  selectedInsect.value = insect;
}
function selectPlot(plot) {
  selectedPlant.value = plot.plant || null;
  selectedInsect.value = plot.insects[0] || null;
}
function startSimulation() {
  simulationStatus.value.summary = "Simulation démarrée.";
}
function pauseSimulation() {
  simulationStatus.value.summary = "Simulation en pause.";
}
function stepSimulation() {
  simulationStatus.value.step++;
  simulationStatus.value.summary = `Étape ${simulationStatus.value.step} effectuée.`;
}
</script>

<template>
  <div
    class="min-h-screen bg-gradient-to-b from-white via-blue-50 to-green-50 flex flex-col items-center"
  >
    <!-- Header -->
    <header class="w-full max-w-2xl mx-auto py-8 text-center">
      <div class="flex flex-col items-center gap-2">
        <span class="text-3xl">🌱</span>
        <h1 class="text-2xl font-bold text-gray-700 tracking-tight">
          Simulation Potager Automatisé
        </h1>
        <span class="text-sm text-gray-400"
          >Université Cadi Ayyad - EST Safi</span
        >
      </div>
    </header>

    <main class="w-full max-w-2xl flex flex-col gap-6 px-2 pb-8">
      <section class="bg-white/90 rounded-2xl shadow p-5">
        <PlantList :plants="plants" @select="selectPlant" />
      </section>
      <section class="bg-white/90 rounded-2xl shadow p-5">
        <InsectList :insects="insects" @select="selectInsect" />
      </section>
      <section
        class="bg-white/90 rounded-2xl shadow p-5 flex flex-col items-center"
      >
        <PlotGrid :plots="plots" @selectPlot="selectPlot" />
      </section>
      <section class="flex flex-col md:flex-row gap-6">
        <div class="flex-1 bg-white/90 rounded-2xl shadow p-5">
          <PlantDetail v-if="selectedPlant" :plant="selectedPlant" />
        </div>
        <div class="flex-1 bg-white/90 rounded-2xl shadow p-5">
          <InsectDetail v-if="selectedInsect" :insect="selectedInsect" />
        </div>
      </section>
      <section class="bg-white/90 rounded-2xl shadow p-5">
        <TreatmentDeviceList :devices="devices" />
      </section>
      <section
        class="bg-white/90 rounded-2xl shadow p-5 flex flex-col md:flex-row items-center justify-between gap-4"
      >
        <SimulationControl
          @start="startSimulation"
          @pause="pauseSimulation"
          @step="stepSimulation"
        />
        <SimulationStatus :status="simulationStatus" />
      </section>
    </main>

    <!-- Footer -->
    <footer
      class="w-full max-w-2xl mx-auto py-6 text-center text-gray-300 text-xs mt-auto"
    >
      © {{ new Date().getFullYear() }} Simulation Potager Automatisé — Projet
      Universitaire
    </footer>
  </div>
</template>

<style scoped>
body {
  font-family: "Inter", system-ui, Avenir, Helvetica, Arial, sans-serif;
}
</style>
