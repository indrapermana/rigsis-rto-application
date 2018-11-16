import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRig } from 'app/shared/model/rig.model';

@Component({
    selector: 'jhi-rig-detail',
    templateUrl: './rig-detail.component.html'
})
export class RigDetailComponent implements OnInit {
    rig: IRig;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rig }) => {
            this.rig = rig;
        });
    }

    previousState() {
        window.history.back();
    }
}
