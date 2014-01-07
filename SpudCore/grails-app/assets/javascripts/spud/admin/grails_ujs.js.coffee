
linkClickSelector = 'a[data-confirm], a[data-method], a[data-remote]'

initializeUJS = ->
  console.log "Initializing UJS"
  $(document).delegate linkClickSelector, 'click.grails', linkClickHandler

linkClickHandler = (e) ->
  link = $(this)
  if (!allowAction(link))
    e.stopImmediatePropagation()
    return false

  if link.data('method')
    handleMethod(link)
    return false


fireEvent = (object, name, data) ->
  event = $.Event(name)
  object.trigger event, data
  return event.result != false


allowAction = (element) ->
  message  = element.data 'confirm'
  answer   = false
  callback = false

  if !message
    return true
  if fireEvent(element, 'confirm')
    answer = confirm(message)
    callback = fireEvent(element, 'confirm:complete', [answer])
  return answer && callback

handleMethod = (link) ->
  method = link.data('method')
  href   = link.attr('href')
  target = link.attr('target')

  form   = $('<form method="post" action="' + href + '"></form>')
  form   = $('<form method="' + method + '" action="' + href + '"></form>')
  # metadataInput = '<input name="_method" value="' + method + '" type="hidden" />';

  if target
    form.attr('target',target)
    # append(metadataInput)
  form.hide().appendTo('body');
  form.submit()

initializeUJS()
