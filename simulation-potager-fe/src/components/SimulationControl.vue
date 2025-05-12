<template>
  <div class="flex flex-col items-center py-4">
    <h2 class="text-2xl font-bold mb-4 text-cyan-700">
      Contrôle de la Simulation
    </h2>
    <div class="flex space-x-4 mb-4">
      <button
        @click="startSimulation"
        :disabled="simulationState === 'running'"
        class="px-6 py-2 bg-green-500 text-black rounded-lg shadow hover:bg-green-600 transition disabled:opacity-50 flex items-center"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="h-5 w-5 mr-2"
          fill="none"
          viewBox="0 0 24 24"
          stroke="black"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M5 3v18l15-9-15-9z"
          />
        </svg>
        Démarrer
      </button>
      <button
        @click="pauseSimulation"
        :disabled="simulationState !== 'running'"
        class="px-6 py-2 bg-yellow-500 text-black rounded-lg shadow hover:bg-yellow-600 transition disabled:opacity-50 flex items-center"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="h-5 w-5 mr-2"
          fill="none"
          viewBox="0 0 24 24"
          stroke="black"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M10 9v6m4-6v6"
          />
        </svg>
        Pause
      </button>
      <button
        @click="stepSimulation"
        :disabled="simulationState === 'running'"
        class="px-6 py-2 bg-blue-500 text-black rounded-lg shadow hover:bg-blue-600 transition disabled:opacity-50 flex items-center"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="h-5 w-5 mr-2"
          fill="none"
          viewBox="0 0 24 24"
          stroke="black"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M16 17l-4-4m0 0l4-4m-4 4H4m16 4V7a2 2 0 00-2-2H6a2 2 0 00-2 2v10a2 2 0 002 2h12a2 2 0 002-2z"
          />
        </svg>
        Étape
      </button>
    </div>
    <div class="mt-2">
      <span
        class="inline-block px-4 py-1 rounded-full text-white font-semibold"
        :class="{
          'bg-green-500': simulationState === 'running',
          'bg-yellow-500': simulationState === 'paused',
          'bg-gray-400': simulationState === 'stopped',
        }"
      >
        {{ statusLabel }}
      </span>
    </div>
  </div>
</template>

<script>
export default {
  name: "SimulationControl",
  data() {
    return {
      simulationState: "stopped", // 'running', 'paused', 'stopped'
      simulationInterval: null,
      updateInterval: 1000, // Update every second
    };
  },
  computed: {
    statusLabel() {
      switch (this.simulationState) {
        case "running":
          return "En cours";
        case "paused":
          return "En pause";
        default:
          return "Arrêtée";
      }
    },
  },
  methods: {
    startSimulation() {
      this.simulationState = "running";
      // Start the simulation cycle
      this.simulationInterval = setInterval(() => {
        this.$emit("simulation-step");
      }, this.updateInterval);
    },
    pauseSimulation() {
      this.simulationState = "paused";
      // Clear the interval when paused
      if (this.simulationInterval) {
        clearInterval(this.simulationInterval);
        this.simulationInterval = null;
      }
    },
    stepSimulation() {
      // Simulate a single step
      this.simulationState = "paused";
      this.$emit("simulation-step");
    },
  },
  beforeUnmount() {
    // Clean up the interval when component is destroyed
    if (this.simulationInterval) {
      clearInterval(this.simulationInterval);
    }
  },
};
</script>

<style scoped></style>
