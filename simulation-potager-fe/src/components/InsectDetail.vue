<template>
  <div
    class="max-w-md mx-auto mt-10 bg-white rounded-xl shadow-lg p-8 flex flex-col items-center"
  >
    <div
      class="w-20 h-20 bg-blue-200 rounded-full flex items-center justify-center mb-6"
    >
      <span class="text-4xl">🐞</span>
    </div>
    <h2 class="text-2xl font-bold text-blue-700 mb-2">
      {{ props.insect.species }}
    </h2>
    <div class="text-gray-700 w-full">
      <div class="flex justify-between py-2 border-b">
        <span class="font-semibold">Sexe :</span>
        <span>{{ props.insect.sex }}</span>
      </div>
      <div class="flex justify-between py-2 border-b">
        <span class="font-semibold">Indice de santé :</span>
        <span
          :class="
            props.insect.healthIndex > 5
              ? 'text-green-600 font-semibold'
              : 'text-red-500 font-semibold'
          "
        >
          {{ props.insect.healthIndex }}
        </span>
      </div>
      <div class="flex justify-between py-2 border-b">
        <span class="font-semibold">Mobilité :</span>
        <span>{{ props.insect.mobility }}</span>
      </div>
      <div class="flex justify-between py-2 border-b">
        <span class="font-semibold">Résistance :</span>
        <span>{{ props.insect.resistance }}</span>
      </div>
      <div class="flex justify-between py-2">
        <span class="font-semibold">Parcelle :</span>
        <span>{{ props.insect.plot }}</span>
      </div>
    </div>
    <div class="mt-6 w-full">
      <slot />
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  insect: {
    type: Object,
    default: () => ({
      id: 1,
      species: "Coccinelle",
      sex: "Femelle",
      healthIndex: 8,
      mobility: "Haute",
      resistance: "Forte",
      plot: "A2",
    }),
  },
});
</script>

<style scoped></style>
