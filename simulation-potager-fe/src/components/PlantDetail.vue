<template>
  <div
    class="max-w-md mx-auto mt-10 bg-white rounded-xl shadow-lg p-8 flex flex-col items-center"
  >
    <div
      class="w-20 h-20 bg-green-200 rounded-full flex items-center justify-center mb-6"
    >
      <span class="text-4xl">{{ prop.plant.drageonnante ? "🌿" : "🌱" }}</span>
    </div>
    <h2 class="text-2xl font-bold text-green-700 mb-2">
      {{ props.plant.species }}
    </h2>
    <div class="text-gray-700 w-full">
      <div class="flex justify-between py-2 border-b">
        <span class="font-semibold">Âge :</span>
        <span>{{ props.plant.age }} jours</span>
      </div>
      <div class="flex justify-between py-2 border-b">
        <span class="font-semibold">Maturité :</span>
        <span
          :class="
            props.plant.isMature
              ? 'text-green-600 font-semibold'
              : 'text-yellow-500 font-semibold'
          "
        >
          {{ props.plant.isMature ? "Oui" : "Non" }}
        </span>
      </div>
      <div class="flex justify-between py-2 border-b">
        <span class="font-semibold">Parcelle :</span>
        <span>{{ props.plant.plot }}</span>
      </div>
      <div class="flex justify-between py-2">
        <span class="font-semibold">Espèce :</span>
        <span>{{ props.plant.species }}</span>
      </div>
    </div>
    <div class="mt-6 w-full">
      <slot />
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  plant: {
    type: Object,
    default: () => ({
      id: 1,
      species: "Tomate",
      age: 3,
      isMature: false,
      plot: "A1",
    }),
  },
});
</script>

<style scoped></style>
