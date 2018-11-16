/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RtoAppTestModule } from '../../../test.module';
import { UnitTypeItemDeleteDialogComponent } from 'app/entities/unit-type-item/unit-type-item-delete-dialog.component';
import { UnitTypeItemService } from 'app/entities/unit-type-item/unit-type-item.service';

describe('Component Tests', () => {
    describe('UnitTypeItem Management Delete Component', () => {
        let comp: UnitTypeItemDeleteDialogComponent;
        let fixture: ComponentFixture<UnitTypeItemDeleteDialogComponent>;
        let service: UnitTypeItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [UnitTypeItemDeleteDialogComponent]
            })
                .overrideTemplate(UnitTypeItemDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UnitTypeItemDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UnitTypeItemService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
