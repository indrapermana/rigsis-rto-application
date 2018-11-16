import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDerivedUnit } from 'app/shared/model/derived-unit.model';

@Component({
    selector: 'jhi-derived-unit-detail',
    templateUrl: './derived-unit-detail.component.html'
})
export class DerivedUnitDetailComponent implements OnInit {
    derivedUnit: IDerivedUnit;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ derivedUnit }) => {
            this.derivedUnit = derivedUnit;
        });
    }

    previousState() {
        window.history.back();
    }
}
