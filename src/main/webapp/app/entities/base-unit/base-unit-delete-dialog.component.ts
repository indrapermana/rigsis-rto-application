import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBaseUnit } from 'app/shared/model/base-unit.model';
import { BaseUnitService } from './base-unit.service';

@Component({
    selector: 'jhi-base-unit-delete-dialog',
    templateUrl: './base-unit-delete-dialog.component.html'
})
export class BaseUnitDeleteDialogComponent {
    baseUnit: IBaseUnit;

    constructor(private baseUnitService: BaseUnitService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.baseUnitService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'baseUnitListModification',
                content: 'Deleted an baseUnit'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-base-unit-delete-popup',
    template: ''
})
export class BaseUnitDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ baseUnit }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BaseUnitDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.baseUnit = baseUnit;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
