<script setup>
import { ref, onMounted, computed } from "vue";
import PlantList from "./components/PlantList.vue";
import PlantDetail from "./components/PlantDetail.vue";
import InsectList from "./components/InsectList.vue";
import InsectDetail from "./components/InsectDetail.vue";
import PlotGrid from "./components/PlotGrid.vue";
import TreatmentDeviceList from "./components/TreatmentDeviceList.vue";
import SimulationControl from "./components/SimulationControl.vue";
import SimulationStatus from "./components/SimulationStatus.vue";
import plotService from "./services/plotService";
import plantService from "./services/plantService";
import insectService from "./services/insectService";
import deviceService from "./services/deviceService";

const plots = ref([]);
const plotGridRef = ref(null);
const plants = ref([]);
const insects = ref([]);
let selectedPlant = ref(null);
let selectedInsect = ref(null);
const currentStep = ref(0);
const devices = ref([]);

onMounted(async () => {
  const res = await plotService.getAll();
  console.log("Fetched plots:", res.data, Array.isArray(res.data));
  plots.value = Array.isArray(res.data) ? res.data : Object.values(res.data);
  // Fetch devices on mount
  const deviceRes = await deviceService.getAll();
  devices.value = deviceRes.data;
});

async function handleSimulationStep() {
  if (plotGridRef.value && plotGridRef.value.handleSimulationStep) {
    await plotGridRef.value.handleSimulationStep();
  }
  // Fetch updated plants and insects
  const [plantRes, insectRes, deviceRes] = await Promise.all([
    plantService.getAll(),
    insectService.getAll(),
    deviceService.getAll(),
  ]);
  plants.value = plantRes.data;
  insects.value = insectRes.data;
  devices.value = deviceRes.data;

  // Re-link selected plant/insect to the updated object
  if (selectedPlant.value) {
    selectedPlant.value =
      plants.value.find((p) => p.id === selectedPlant.value.id) || null;
  }
  if (selectedInsect.value) {
    selectedInsect.value =
      insects.value.find((i) => i.id === selectedInsect.value.id) || null;
  }
  // Increment simulation step
  currentStep.value++;
}

const simulationStatus = computed(() => ({
  step: currentStep.value,
  plants: plants.value.length,
  insects: insects.value.length,
  devices: devices.value.length,
  summary: "La simulation progresse normalement. Aucun incident détecté.",
}));
</script>

<template>
  <div
    class="min-h-screen grid grid-rows-[auto_1fr_auto] bg-gradient-to-b from-white via-blue-50 to-green-50"
  >
    <!-- Header -->
    <header class="w-full max-w-7xl mx-auto py-8 text-center">
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

    <!-- Main grid -->
    <main class="w-full max-w-7xl mx-auto grid grid-cols-12 gap-8 px-4 pb-8">
      <!-- Left sidebar: Plants & Insects -->
      <aside class="col-span-3 flex flex-col space-y-6">
        <section
          class="bg-white/90 rounded-2xl shadow p-5 flex-1 overflow-y-auto"
        >
          <PlantList :plants="plants" @select="selectPlant" />
        </section>
        <section
          class="bg-white/90 rounded-2xl shadow p-5 flex-1 overflow-y-auto"
        >
          <InsectList :insects="insects" @select="selectInsect" />
        </section>
      </aside>

      <!-- Center: Plot + Controls -->
      <section class="col-span-6 flex flex-col space-y-6">
        <div class="bg-white/90 rounded-2xl shadow p-5 flex-1 overflow-hidden">
          <PlotGrid
            ref="plotGridRef"
            :plots="plots"
            :devices="devices"
            :currentStep="currentStep"
            @selectPlot="selectPlot"
          />
        </div>
        <!-- <div class="bg-white/90 rounded-2xl shadow p-5">
          <SimulationControl
            @start="startSimulation"
            @pause="pauseSimulation"
            @step="stepSimulation"
          />
        </div> -->
      </section>
      <div
        class="fixed bottom-4 left-1/2 transform -translate-x-1/2 bg-white/90 rounded-2xl shadow-lg p-4 z-50"
        style="width: 90%; max-width: 400px"
      >
        <SimulationControl @simulation-step="handleSimulationStep" />
      </div>
      <!-- Right sidebar: Stats & Devices -->
      <aside class="col-span-3 flex flex-col space-y-6 gap-y-4">
        <section class="bg-white/90 rounded-2xl shadow p-5">
          <SimulationStatus :status="simulationStatus" />
        </section>
        <section
          class="bg-white/90 rounded-2xl shadow p-5 flex-1 overflow-y-auto"
        >
          <TreatmentDeviceList :devices="devices" :currentStep="currentStep" />
        </section>
        <section class="bg-white/90 rounded-2xl shadow p-5 flex-1">
          <PlantDetail v-if="selectedPlant" :plant="selectedPlant" />
          <InsectDetail v-else-if="selectedInsect" :insect="selectedInsect" />
          <p v-else class="text-gray-500">
            Sélectionnez une plante ou un insecte.
          </p>
        </section>
      </aside>
    </main>

    <!-- Footer -->
    <footer
      class="w-full max-w-7xl mx-auto py-6 text-center text-gray-300 text-xs"
    >
      © {{ new Date().getFullYear() }} Simulation Potager Automatisé — Projet
      Universitaire
    </footer>
  </div>
</template>

<style scoped>
/* (You can drop the body rule now that everything is in a wrapping div) */
</style>
