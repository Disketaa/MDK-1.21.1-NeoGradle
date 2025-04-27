---
name: Bug
about: Create a bug report
title: ''
labels: bug
assignees: Disketaa

---

![](https://github.com/Disketaa/Harmonium/blob/workspace/workspace/assets/icons/icon_number_1.png?raw=true) **Information:**
body:
- type: textarea
  id: version
  attributes:
    label: Mod version:
    description: "(e.g: 0.4.1)."
    value: |
    render: bash
  validations:
    required: true
body:
- type: dropdown
  id: modloader
  attributes:
    label: Modloader:
    options:
      - Fabric
      - Neoforge
  validations:
    required: true

![](https://github.com/Disketaa/Harmonium/blob/workspace/workspace/assets/icons/icon_number_1.png?raw=true) **Describe the bug:**
> A clear and concise description of what the bug is.

![](https://github.com/Disketaa/Harmonium/blob/workspace/workspace/assets/icons/icon_number_2.png?raw=true) **To reproduce:**
>Steps to reproduce the behavior:

![](https://github.com/Disketaa/Harmonium/blob/workspace/workspace/assets/icons/icon_number_3.png?raw=true) **Expected behavior:**
>A clear and concise description of what you expected to happen.

![](https://github.com/Disketaa/Harmonium/blob/workspace/workspace/assets/icons/icon_number_4.png?raw=true) **Screenshots:**
>If applicable, add screenshots to help explain your problem.

![](https://github.com/Disketaa/Harmonium/blob/workspace/workspace/assets/icons/icon_number_5.png?raw=true) **Additional context:**
>Add any other context about the problem here.
