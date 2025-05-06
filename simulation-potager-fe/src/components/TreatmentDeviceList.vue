<template>
  <div class="container mx-auto py-8">
    <h2 class="text-3xl font-bold mb-6 text-purple-700 text-center">
      Dispositifs de Traitement
    </h2>
    <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
      <div
        v-for="device in devices"
        :key="device.id"
        class="bg-white rounded-xl shadow-lg p-6 flex flex-col hover:scale-105 transition-transform"
      >
        <div class="flex items-center mb-4">
          <span class="text-2xl mr-2">
            <span v-if="device.type === 'Arrosage'">💧</span>
            <span v-else-if="device.type === 'Engrais'">🌾</span>
            <span v-else-if="device.type === 'Insecticide'">🧴</span>
            <span v-else>🔧</span>
          </span>
          <h3 class="text-xl font-semibold text-gray-800">{{ device.type }}</h3>
        </div>
        <div class="text-gray-700 mb-2">
          <span class="font-semibold">Programme d'activation :</span>
          <ul class="ml-4 mt-1 list-disc">
            <li>
              Début :
              <span class="font-medium">{{ device.program.start }}</span>
            </li>
            <li>
              Durée :
              <span class="font-medium">{{ device.program.duration }} min</span>
            </li>
            <li>
              Type de traitement :
              <span class="font-medium">{{
                device.program.treatmentType
              }}</span>
            </li>
          </ul>
        </div>
        <div class="text-gray-700">
          <span class="font-semibold">Parcelles concernées :</span>
          <div class="flex flex-wrap mt-1">
            <span
              v-for="plot in device.affectedPlots"
              :key="plot"
              class="bg-purple-100 text-purple-700 rounded-full px-3 py-0.5 text-xs font-bold mr-2 mb-2"
              >{{ plot }}</span
            >
          </div>
        </div>
      </div>
    </div>
    <div v-if="devices.length === 0" class="text-center text-gray-500 mt-10">
      Aucun dispositif à afficher.
    </div>
  </div>
</template>

<script>
export default {
  name: "TreatmentDeviceList",
  data() {
    return {
      devices: [
        {
          id: 1,
          type: "Arrosage",
          program: { start: "08:00", duration: 10, treatmentType: "Eau" },
          affectedPlots: ["A1", "A2", "B1"],
        },
        {
          id: 2,
          type: "Engrais",
          program: { start: "09:00", duration: 5, treatmentType: "Engrais" },
          affectedPlots: ["A3", "B2"],
        },
        {
          id: 3,
          type: "Insecticide",
          program: {
            start: "10:00",
            duration: 7,
            treatmentType: "Insecticide",
          },
          affectedPlots: ["A4", "B3", "B4"],
        },
      ],
    };
  },
};
</script>

<style scoped></style>
