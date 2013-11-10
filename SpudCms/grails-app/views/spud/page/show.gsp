
	<g:each var="partial" in="${page.partials}">
		<%println "Rendering ${partial.symbolName}"%>
		<content tag="${partial.symbolName}">
			${raw(partial.contentProcessed)}
		</content>
	</g:each>

