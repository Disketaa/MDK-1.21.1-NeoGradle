name: Bug Report
description: "For reporting bugs and other issues"
labels:
  - S-needs-triage
body:
  - type: markdown
    attributes:
      value: >-
  - type: textarea
    id: description
    attributes:
      label: Bug Description
      description: >-
        Use this section to describe the issue you are experiencing in as much depth as possible. The description should
        explain what behavior you were expecting, and why you believe the issue to be a bug. If the issue you are reporting
        only occurs with specific mods installed, then provide the name and version of each mod.

        **Hint:** If you have any screenshots, videos, or other information that you feel is necessary to
        explain the issue, you can attach them here.
  - type: textarea
    id: description-reproduction-steps
    attributes:
      label: Reproduction Steps
      description: >-
        Provide as much information as possible on how to reproduce this bug. Make sure your instructions are as clear and
        concise as possible, because other people will need to be able to follow your guide in order to re-create the issue.
        
        **Hint:**  A common way to fill this section out is to write a step-by-step guide.
    validations:
      required: true
